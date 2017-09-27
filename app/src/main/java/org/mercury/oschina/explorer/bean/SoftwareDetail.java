package org.mercury.oschina.explorer.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mercury on 2017/9/26.
 */

public class SoftwareDetail implements Parcelable {


    /**
     * recordtime : 2009-11-22 12:11:22
     * languages : Java
     * os : 跨平台
     * document : https://www.oschina.net/action/project/go?id=313&p=doc
     * title : Spring
     * body : <p><strong>Spring Framework</strong> 是一个开源的Java/Java
     * EE全功能栈（full-stack）的应用程序框架，以Apache许可证形式发布，也有
     * .NET平台上的<a href="http://www.oschina.net/p/spring.net" rel="nofollow">移植版本</a>。该框架基于 Expert
     * One-on-One Java EE Design and Development（ISBN 0-7645-4385-7）一书中的代码，最初由 Rod Johnson 和
     * Juergen Hoeller等开发。Spring Framework 提供了一个简易的开发方式，这种开发方式，将避免那些可能致使底层代码变得繁杂混乱的大量的属性文件和帮助类。</p>
     <p><img src="http://static.oschina.net/uploads/space/2013/0414/214124_rYSe_12.jpg" alt=""></p>
     <p>Spring 中包含的关键特性：</p>
     <ul>
     <li><p>强大的基于 JavaBeans 的采用控制翻转（Inversion of Control，IoC）原则的配置管理，使得应用程序的组建更加快捷简易。</p></li>
     <li><p>一个可用于从 applet 到 Java EE 等不同运行环境的核心 Bean 工厂。</p></li>
     <li><p>数据库事务的一般化抽象层，允许宣告式(Declarative)事务管理器，简化事务的划分使之与底层无关。</p></li>
     <li><p>内建的针对 JTA 和 单个 JDBC 数据源的一般化策略，使 Spring 的事务支持不要求 Java EE 环境，这与一般的 JTA 或者 EJB CMT
     相反。</p></li>
     <li><p>JDBC 抽象层提供了有针对性的异常等级(不再从SQL异常中提取原始代码), 简化了错误处理, 大大减少了程序员的编码量. 再次利用JDBC时，你无需再写出另一个
     '终止' (finally) 模块. 并且面向JDBC的异常与Spring 通用数据访问对象 (Data Access Object) 异常等级相一致.</p></li>
     <li><p>以资源容器，DAO 实现和事务策略等形式与 Hibernate，JDO 和 iBATIS SQL Maps 集成。利用众多的翻转控制方便特性来全面支持,
     解决了许多典型的Hibernate集成问题. 所有这些全部遵从Spring通用事务处理和通用数据访问对象异常等级规范.</p></li>
     <li><p>灵活的基于核心 Spring 功能的 MVC 网页应用程序框架。开发者通过策略接口将拥有对该框架的高度控制，因而该框架将适应于多种呈现(View)技术，例如
     JSP，FreeMarker，Velocity，Tiles，iText 以及 POI。值得注意的是，Spring 中间层可以轻易地结合于任何基于 MVC 框架的网页层，例如
     Struts，WebWork，或 Tapestry。</p></li>
     <li><p>提供诸如事务管理等服务的面向方面编程框架。</p></li>
     </ul>
     <p>在设计应用程序Model时，MVC 模式（例如Struts）通常难于给出一个简洁明了的框架结构。Spring却具有能够让这部分工作变得简单的能力。程序开发员们可以使用Spring
     的 JDBC 抽象层重新设计那些复杂的框架结构。</p>
     <p><strong>在线Javadoc：</strong
     ><a href="http://tool.oschina.net/apidocs/apidoc?api=Spring-3.1.1" target="_blank" rel="nofollow">http://tool.oschina.net/apidocs/apidoc?api=Spring-3.1.1</a></p>
     * url : https://www.oschina.net/p/spring
     * license : Apache
     * download : https://www.oschina.net/action/project/go?id=313&p=download
     * logo : https://www.oschina.net/img/logo/spring.png?t=1451961935000
     * id : 313
     * favorite : 0     如果是已登录的用户(0代表未收藏    1代表已收藏)
     * extensionTitle : J2EE框架
     * homepage : https://www.oschina.net/action/project/go?id=313&p=home
     */

    private String recordtime;
    private String languages;
    private String os;
    private String document;
    private String title;
    private String body;
    private String url;
    private String license;
    private String download;
    private String logo;
    private int    id;
    private int    favorite;
    private String extensionTitle;
    private String homepage;

    public String getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(String recordtime) {
        this.recordtime = recordtime;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getExtensionTitle() {
        return extensionTitle;
    }

    public void setExtensionTitle(String extensionTitle) {
        this.extensionTitle = extensionTitle;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recordtime);
        dest.writeString(this.languages);
        dest.writeString(this.os);
        dest.writeString(this.document);
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeString(this.url);
        dest.writeString(this.license);
        dest.writeString(this.download);
        dest.writeString(this.logo);
        dest.writeInt(this.id);
        dest.writeInt(this.favorite);
        dest.writeString(this.extensionTitle);
        dest.writeString(this.homepage);
    }

    public SoftwareDetail() {
    }

    protected SoftwareDetail(Parcel in) {
        this.recordtime = in.readString();
        this.languages = in.readString();
        this.os = in.readString();
        this.document = in.readString();
        this.title = in.readString();
        this.body = in.readString();
        this.url = in.readString();
        this.license = in.readString();
        this.download = in.readString();
        this.logo = in.readString();
        this.id = in.readInt();
        this.favorite = in.readInt();
        this.extensionTitle = in.readString();
        this.homepage = in.readString();
    }

    public static final Parcelable.Creator<SoftwareDetail> CREATOR = new Parcelable.Creator<SoftwareDetail>() {
        @Override
        public SoftwareDetail createFromParcel(Parcel source) {
            return new SoftwareDetail(source);
        }

        @Override
        public SoftwareDetail[] newArray(int size) {
            return new SoftwareDetail[size];
        }
    };
}
