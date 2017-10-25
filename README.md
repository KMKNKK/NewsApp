# NewsApp
 This is a project just for studying Android , you are welcome to fork it. 

 这是一个简单的新闻APP，采用了Google Material Design模式。界面功能主要实现了Swiplayout下拉刷新、顶部菜单栏、隐藏菜单栏等；<br>
 用户可以使用这款APP浏览时下新闻热点，注册/登陆账户以收藏自己喜欢的新闻，便于今后浏览。用户还可以在主页查看地区天气。<br>
 使用到的开源技术有：OkHttp、Gson、JDBC-mysql、Google原生组件、Swiplayput；<br>

#
 由于服务器问题，暂时无法提供需要借助于服务器mysql的用户系统服务（收藏与取消收藏新闻），你仍然可以浏览新闻和天气。<br>
 <br>
 如果你想使用用户系统并且你拥有自己的服务器的话，欢迎将JDBC_connection中的：<br>
 con = DriverManager.getConnection("jdbc:mysql://10.0.2.2:3305/newsapp", "root", "root");<br>
 10.0.2.2:3305改为你的服务器的（ip：端口号），创建相应的数据库并且打开数据库的远程连接权限。<br>
#
 以下是数据库具体信息：<br>
 数据库名(databasename):  NewsAPP<br>

 包含表(tables)： User_Manager（<br>
                      Username        char(20)，<br>
                      Password        char(50)<br>
                               ）<br>
                  News_Collection(<br>
                      NewsName        char(255),<br>
                      NewsContant       char(255), <br>     
                      NewspictureURL    char(255),<br>
                      Username          char(20)<br>
                       )<br>
