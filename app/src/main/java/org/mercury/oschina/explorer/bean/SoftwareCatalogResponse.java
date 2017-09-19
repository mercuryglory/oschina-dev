package org.mercury.oschina.explorer.bean;

import org.mercury.oschina.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Mercury on 2017/9/19.
 */

public class SoftwareCatalogResponse extends BaseBean{


    /**
     * softwareTypes : [{"name":"跨平台","tag":35},{"name":"Windows","tag":36},{"name":"Linux",
     * "tag":37},{"name":"BSD","tag":38},{"name":"UNIX","tag":156},{"name":"OS X","tag":39},
     * {"name":"Symbian","tag":40},{"name":"J2ME","tag":200},{"name":"嵌入式","tag":41},
     * {"name":"Android","tag":189},{"name":"iPhone/iPad/iPod","tag":190},{"name":"Windows
     * Phone/Mobile","tag":208},{"name":"Meego","tag":266},{"name":"Moblin","tag":211},
     * {"name":"Firefox OS","tag":442}]
     * notice : {"referCount":0,"replyCount":0,"msgCount":0,"fansCount":0}
     */

    private List<SoftwareCatalog> softwareTypes;


    public List<SoftwareCatalog> getSoftwareTypes() {
        return softwareTypes;
    }

    public void setSoftwareTypes(List<SoftwareCatalog> softwareTypes) {
        this.softwareTypes = softwareTypes;
    }


}
