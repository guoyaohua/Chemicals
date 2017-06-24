package com.guoyaohua.chemicals.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by John Kwok on 2017/6/24.
 */

public class NameSearchEntity {
    public static List<List<HashMap<String, Object>>> CN_SearchResult = new ArrayList<List<HashMap<String, Object>>>(); //用来保存返回的各个分类中化学品的名称。
    public static List<List<HashMap<String, Object>>> EN_SearchResult = new ArrayList<List<HashMap<String, Object>>>(); //用来保存返回的各个分类中化学品的名称。
}
