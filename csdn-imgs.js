"use strict";

const Axios = require('axios')
var fs = require('fs');
var markdownLinkExtractor = require('markdown-link-extractor');
var Path = require('path');
var URL = require("url");

var directoryPath = Path.join("./source/_posts");
var fileList = fs.readdirSync(directoryPath);
fileList
    .flatMap(file => {
        if (file.includes(".md")) {
            var markdown = fs.readFileSync(Path.join("./source/_posts", file)).toString();
            return markdownLinkExtractor(markdown);
        }
        else {
            return [];
        }
    })
    .filter(link => {
        return link.includes("csdn") && (link.includes("gif"));
    })
    .map(function (url) {
        console.log(url);

        var parsed = URL.parse(url);
        console.log(parsed.pathname);

        const path = Path.join('./source/images', parsed.pathname);
        console.log(path);

        return Axios({
            responseType: 'stream',
            url: url,
            method: "get",
            headers: {
                Cookie: "haha",
                accept: "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
                "accept-encoding": "gzip, deflate, br",
                "accept-language": "en - US, en; q=0.9, zh- CN; q = 0.8, zh - TW; q = 0.7, zh - HK; q = 0.6, zh - SG; q = 0.5, zh; q = 0.4",
                "cache-control": "no - cache",
                "dnt": "1",
                "pragma": "no - cache",
                "sec-fetch-mode": "navigate",
                'referer': "https://blog.csdn.net/cuipengfei1/article/details/6456656",
                "sec-fetch-site": "none",
                "sec-fetch-user": " ? 1",
                "upgrade-insecure-requests": "1",
                "user-agent": "Mozilla / 5.0(Macintosh; Intel Mac OS X 10_15_1) AppleWebKit / 537.36(KHTML, like Gecko) Chrome / 79.0.3945.79 Safari / 537.36"
            }
        })
            .then(function (response) {
                fs.mkdirSync(Path.dirname(path), { recursive: true }, (err) => {
                    if (err) throw err;
                });
                return response.data.pipe(fs.createWriteStream(path));
            })
            .catch(function (error) {
                console.log(error);
            })
            .finally(function () {
            });
    })
    .forEach(p => {
        p.then(function (response) {
        }).catch(function (error) {
            console.log(error);
        })
    });;