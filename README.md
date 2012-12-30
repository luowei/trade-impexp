#trade-impexp
[trade-impexp](https://github.com/luowei/trade-impexp)是一个maven项目，其中包含了多个模块；


##trade-persistent
[trade-persistent](https://github.com/luowei/trade-impexp/tree/master/trade-persistent)是trade-globle持久化的模块接口部分，待完成中。。

##trade-persistent-impl
[trade-persistent-impl](https://github.com/luowei/trade-impexp/tree/master/trade-persistent-impl)是trade-globle持久化的模块实现部分，待完成中。。

##trade-service
[trade-service](https://github.com/luowei/trade-impexp/tree/master/trade-service)是trade-globle业务处理的模块接口部分，待完成中。。

##trade-service-impl
[trade-service-impl](https://github.com/luowei/trade-impexp/tree/master/trade-service-impl)是trade-globle业务处理的模块实现部分，待完成中。。

##trade-util
[trade-util](https://github.com/luowei/trade-impexp/tree/master/trade-util)是trade-globle工具模块部分，待完成中。。

##trade-view
[trade-view](https://github.com/luowei/trade-impexp/tree/master/trade-view)是trade-globle视图模块部分，待完成中。。



##========================================================================================
##其它:
1.先写测试再写实现，速度更快，成效更好；
2.参数尽可能采用对象传递，避免用一般类型传递；

---------------------------------
springside:
https://github.com/springside/springside4

##maven -pl 选项
maven 在使用 -pl 选项指定的值过滤模块的时候，通过两种方式，一是把 -pl 选项的值当做 groupId:artifactId 来查找，
其次把 -pl 选项的值作为相对路径来查找，相对于用户运行 maven 时的工作目录。

> 例如有以下项目结构：
> all [org.apache.maven:test]
> |-- m-1 [org.apache.maven:m1]
> |-- m-2 [org.apache.maven:m2]

如果想通过 -pl 选项来指定顶级模块 all 和 m-1 模块，可以使用一下这么命令：
`mvn -pl org.apache.maven:test,m-1 clean install`
