nginx-filter
============

Deprecated use org.apache.catalina.valves.RemoteIpValve instead.
This project will never be updated any more.

============
A filter for java web applications, you can usually configure global server.
```
<filter>
  <filter-name>NginxFilter</filter-name>
  <filter-class>com.ys168.zhanhb.filter.NginxFilter</filter-class>
  <init-param>
    <param-name>excludes</param-name>
    <param-value>61.153.34.35, 10.7.18.100, 127.0.0.1</param-value>
  </init-param>
</filter>
```
