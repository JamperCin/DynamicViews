package com.jamper.viewscreator.CALLBACK;

import allianz.com.jamper.allianz.DATA.Model.remote.BaseResponse;

/**
 * Created by jamper on 2/1/2018.
 */

public interface ChainCallBacks {

    public void execute(String data, retrofit2.Response<BaseResponse> response);

}
