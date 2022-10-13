# jrekvs
A high performance jrekvs developed by Tommy.z ,used Java NIO and Off-heap Memory 

jrekvs NIO模型涉及到的概念:
1.JcacheMain 是jcache的启动类，在该类调用tcp协议监听类
2.TCPNIOAcceptor 是TCP监听逻辑的主要实现，创建专门的线程来处理所有的 IO 事件，并负责分发
3.NIOReactor 是reactor的实现，专门负责维护连接请求队列，包括存放连接请求进入队列、注册selector的读写事件
4.NIOReactorPool 是rector线程池处理类  也是acceptor线程分配 连接给 具体某一个rector 线程的策略上下文

相关资料：
二进制协议开发：
https://github.com/fastio/memcache-sdk/tree/master/src/main/java/com/aliyun/ocs/protocol/memcached/binary
go语言实现的memcached客户端（基于二进制协议）
http://blog.csdn.net/pangudashu/article/details/50958736
报文结构：
http://blog.csdn.net/pangudashu/article/details/50667123
memcached源码分析item过期失效处理以及LRU爬虫：
http://www.faceye.net/search/142629.html


JCache 内部存储涉及到的概念:
-----
1.Item  jcache 存储的最小单元.
2.slab  相当于page 的概念,一个slab由多个item组成.
3.slabclass 一个 slabclass 由多个slab组成.
4.pool  全局唯一的一个容器.负责 slabclass,slab,item 的生命周期.

#### item 可以分为两部分,分别是: 属性部分,和数据部分. 属性部分长度固定,数据部分长度不固定.
|#|字段|说明|
|---|----|-----
|1|prev|指向上一个item 的内存首地址,没有就是0.
|2|next|指向下一个item 的内存首地址,没有就是0.
|3|hnext|hashtable 某一个桶内, 指向下一个item 的内存首地址,hash单向链表
|4|flushtime|当前item 最近一次访问时间. 每次访问都会刷新该字段.
|5|exptime|过期时间
|6|nbytes:|value 的总大小, 包含最后两位\r\n 的长度. 客户端传递过来的数据并没有包含\r\n,存储的时候在最后两位附加上的.
|7|refcount|当前item 被 引用的次数,只在内部使用.
|8|slabs_clsid| 当前 item 属于哪个slabclass
|9|it_flags|item 存储状态标记字段 位运算.
|10|nsuffix|suffix 的长度.        
|11|nkey| key 的长度
|12|cas|  cas  当前item cas 值.  如果 启用cas 的话, 当前字段有值,如果没有启用cas,item结构中没有该字段
|13|suffix| 格式为: '' "flags" '' "nbytes" \r \n  .  '' 代表  一个空的字符<br>例如: 如果 flags=32 （32 代表字符串）, nbytes=15 （ 15 代表 value 的byte[] 长度.）.<br>那么 nsuffix = ''32''15\r\n.<br>
|14|value|将客户端传递进来的value + \r\n后存储.
