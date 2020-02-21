---
title: hand-make-a-distributed-kv-store
date: 2020-02-21 20:54:15
tags:
---

```mermaid
graph TD;
    A-->B;
    A-->C;
    B-->D;
    C-->D;
```

```graphviz
digraph G {

  a1 -> b3;
  b2 -> a3;
  a3 -> a0;
  a3 -> end;
  b3 -> end;

}
```
