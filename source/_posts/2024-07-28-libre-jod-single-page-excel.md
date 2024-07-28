---
title: 解决 LibreOffice 导出Excel 到 PDF 超链接丢失问题
date: 2024-07-28
tags: [LibreOffice, PDF, JODConverter]
---

# 问题描述

在使用LibreOffice的Calc组件将电子表格导出为PDF文件时，如果启用了“Whole sheet export”（整页导出）选项，导出的PDF文件中的超链接将不会保留原始的URL，而是显示为本地文件路径。

这个问题在

1. [LibreOffice 官方论坛上的提问](https://ask.libreoffice.org/t/web-hyperlinks-not-preserved-after-export-to-pdf/96762)
2. [Stack Overflow 上的提问](https://stackoverflow.com/questions/78799273/implementing-a-custom-whole-sheet-export-via-uno-api-to-preserve-hyperlinks-in)

上都有讨论。

# 解决思路

为了解决这个问题，我们考虑使用LibreOffice的UNO API来实现一个自定义的解决方案。这个方案的核心思想是：
- 遍历Calc文档中的每个工作表。
- 计算每个工作表内容适应一页所需的总宽度和高度。
- 根据这些尺寸为每个工作表生成自定义的纸张大小。
- 使用自定义纸张大小导出PDF，同时确保不使用“Whole sheet export”选项，以保留超链接。

# ExcelSinglePageFilter解决方案
针对上述问题，`ExcelSinglePageFilter`Java类实现了自定义的PDF导出过滤器。

以下是该过滤器如何解决问题的详细解析。

# 过滤器初始化与文档检查
`ExcelSinglePageFilter`首先检查传入的文档是否为Excel文档。如果不是，它将直接调用链式调用`chain.doFilter`继续处理。

```java
XSpreadsheetDocument xSpreadsheetDocument = queryInterface(XSpreadsheetDocument.class, document);
if (xSpreadsheetDocument == null) {
    chain.doFilter(context, document);
    return;
}
```

# 工作表遍历与处理
接着，该过滤器遍历所有工作表，并为每个工作表异步执行调整操作。对于隐藏的工作表，它将跳过处理。

```java
String[] sheetNames = xSpreadsheetDocument.getSheets().getElementNames();
CompletableFuture[] futures = Arrays.stream(sheetNames).map(sheetName -> CompletableFuture.runAsync(() -> {
    // ... 省略部分代码 ...
    adjustOneSheet(sheetName, sheet, xPageStyles);
})).toList().toArray(new CompletableFuture[0]);
```

# 处理每个工作表
对于每个工作表，代码首先检查工作表是否可见，然后计算工作表的总宽度和高度，包括单元格和图形对象。

```java
private static void adjustOneSheet(String sheetName, XSpreadsheet sheet, XNameAccess xPageStyles) {
    // 计算工作表的总宽度和高度
    int totalWidth = getTotalWidth(getxColumnRowRange(sheet), getLastColumn(sheet));
    int totalHeight = getTotalHeight(getxColumnRowRange(sheet), getLastRow(sheet));

    // 包括图形对象的尺寸
    Size graphicalSize = getGraphicalObjectsSize(sheet);
    totalWidth = Math.max(totalWidth, graphicalSize.Width);
    totalHeight = Math.max(totalHeight, graphicalSize.Height);

    // 设置页面样式属性
    XPropertySet xPageStyleProps = getPageStyleProps(sheet, xPageStyles);
    xPageStyleProps.setPropertyValue("Size", new Size(totalWidth, totalHeight));
    setMarginToZero(xPageStyleProps);
    xPageStyleProps.setPropertyValue("ScaleToPages", (short) 1);
}
```

# 计算图形对象尺寸
getGraphicalObjectsSize方法用于计算工作表中所有图形对象所占的最大宽度和高度。

```java
private static Size getGraphicalObjectsSize(XSpreadsheet sheet) {
    XDrawPageSupplier drawPageSupplier = queryInterface(XDrawPageSupplier.class, sheet);
    XDrawPage drawPage = drawPageSupplier.getDrawPage();
    int count = drawPage.getCount();
    int maxWidth = 0;
    int maxHeight = 0;

    for (int i = 0; i < count; i++) {
        XShape shape = queryInterface(XShape.class, drawPage.getByIndex(i));
        Point position = shape.getPosition();
        Size size = shape.getSize();
        maxWidth = Math.max(maxWidth, position.X + size.Width);
        maxHeight = Math.max(maxHeight, position.Y + size.Height);
    }

    return new Size(maxWidth, maxHeight);
}
```

# 计算总宽度和总高度
getTotalWidth和getTotalHeight方法分别用于计算工作表的总宽度和总高度。

```java
private static int getTotalWidth(XColumnRowRange columnRowRange, int endColumn) {
    int totalWidth = 0;
    for (int j = 0; j <= endColumn; j++) {
        Object column = columnRowRange.getColumns().getByIndex(j);
        XPropertySet columnProps = queryInterface(XPropertySet.class, column);
        totalWidth += (int) columnProps.getPropertyValue("Width");
    }
    return totalWidth;
}

private static int getTotalHeight(XColumnRowRange columnRowRange, int endRow) {
    int totalHeight = 0;
    for (int i = 0; i <= endRow; i++) {
        Object row = columnRowRange.getRows().getByIndex(i);
        XPropertySet rowProps = queryInterface(XPropertySet.class, row);
        totalHeight += (int) rowProps.getPropertyValue("Height");
    }
    return totalHeight;
}
```

# 完成导出
最后，等待所有异步任务完成后，调用链式调用chain.doFilter继续执行标准的PDF导出流程。

```java
CompletableFuture.allOf(futures).join();
chain.doFilter(context, document);
```

# 代码链接
上述解决方案的原始代码可以在GitHub上找到，链接为：

https://github.com/cuipengfei/jodconverter-samples/blob/main/samples/spring-boot-rest/src/main/java/org/jodconverter/sample/rest/ExcelSinglePageFilter.java

# 总结
ExcelSinglePageFilter通过自定义的PDF导出逻辑，成功避免了使用“Whole sheet export”选项，从而解决了超链接在PDF中丢失的问题。这种方法不仅保留了超链接的完整性，而且还提供了一种灵活的方式来调整每个工作表的显示尺寸，确保它们在PDF中以单页的形式呈现。