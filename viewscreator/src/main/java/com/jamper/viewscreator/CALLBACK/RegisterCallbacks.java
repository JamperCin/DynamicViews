package com.jamper.viewscreator.CALLBACK;


import java.util.List;

import allianz.com.jamper.allianz.DATA.Model.remote.Cutomers;

/**
 * Created by jamper on 6/25/2017.
 */

public interface RegisterCallbacks {

    void execute();

    void getCustomerList(List<Cutomers> list);
}
