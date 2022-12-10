# 

通知的使用场景主要包括:

1. 内容变更-通知相关人员
2. 运行系统出现异常-通知相关人员

## 实际例子- API接口监控

接口监控流程:
1. 设置监控接口定义内容
2. 设置监控任务

## 接口监控配置

- 新增接口:
![img.png](new_add.png)
- 接口内容
![img.png](api_def.png)![]
主要需要输入的是操作定义,操作定义使用通过浏览器 copy as nodejs fetch:

```js
fetch("https://chain.carbonnt.com/explorer/api/nodeGetDetails", {
  "headers": {
    "accept": "application/json, text/plain, */*",
    "accept-language": "zh-CN,zh;q=0.9",
    "cache-control": "no-cache",
    "content-type": "application/json",
    "pragma": "no-cache",
    "sec-ch-ua": "\"Google Chrome\";v=\"105\", \"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"105\"",
    "sec-ch-ua-mobile": "?0",
    "sec-ch-ua-platform": "\"macOS\"",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "same-origin",
    "cookie": "_ga=GA1.1.565174369.1654768273; NSID=irqmE6toJJmTdDLZhQF4jxgo94309e0d; NSID_EXISTS=1",
    "Referer": "https://chain.carbonnt.com/explorer/nodes",
    "Referrer-Policy": "strict-origin-when-cross-origin"
  },
  "body": "{\"client\":\"goquorum\",\"rpcUrl\":\"http://127.0.0.1:8545\"}",
  "method": "POST"
});
```

修改一下请求内容,将这些内容保存到操作服务中既可以

```json
{
  "url": "https://chain.carbonnt.com/explorer/api/nodeGetDetails", 
  "headers": {
    "accept": "application/json, text/plain, */*",
    "accept-language": "zh-CN,zh;q=0.9",
    "cache-control": "no-cache",
    "content-type": "application/json",
    "pragma": "no-cache",
    "sec-ch-ua": "\"Google Chrome\";v=\"105\", \"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"105\"",
    "sec-ch-ua-mobile": "?0",
    "sec-ch-ua-platform": "\"macOS\"",
    "sec-fetch-dest": "empty",
    "sec-fetch-mode": "cors",
    "sec-fetch-site": "same-origin",
    "cookie": "_ga=GA1.1.565174369.1654768273; NSID=irqmE6toJJmTdDLZhQF4jxgo94309e0d; NSID_EXISTS=1",
    "Referer": "https://chain.carbonnt.com/explorer/nodes",
    "Referrer-Policy": "strict-origin-when-cross-origin"
  },
  "body": "{\"client\":\"goquorum\",\"rpcUrl\":\"http://127.0.0.1:8545\"}",
  "method": "POST"
}
```

## Job

- 新增定时任务
![img.png](jobs.png)
- 设置定时时间
![img.png](job_time.png)

quatz-job cron 表达式子生成. 
[在线生成](http://www.cronmaker.com/?0)

如果有出错会进行发送飞书通知


## References

- [cron 表达式](https://cron.qqe2.com/)
- [Quartz  cron表达式在线生成](http://www.cronmaker.com/?0)
- [飞书富文本编辑器](https://open.feishu.cn/tool/cardbuilder?from=howtoguide)
- [飞书富文本说明](https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN?lang=zh-CN)
- [fontawesome 图标](https://fontawesome.com/v4/examples/)