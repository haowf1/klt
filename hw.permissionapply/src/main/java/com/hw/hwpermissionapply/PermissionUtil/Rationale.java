package com.hw.hwpermissionapply.PermissionUtil;

/**
 * Created by Yan Zhenjie on 2016/9/10.
 */
public interface Rationale extends Cancelable {

    /**
     * Go request permission.
     */
    void resume();

}
