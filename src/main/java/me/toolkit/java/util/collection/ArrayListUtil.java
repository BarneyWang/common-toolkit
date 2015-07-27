package me.toolkit.java.util.collection;


import me.toolkit.java.util.number.IntegerUtil;

import java.util.ArrayList;

/**
 * Description: ArrayList util
 *
 * @author wangdi0410@gmail.com
 * @Date Jan 13, 2012
 */
public class ArrayListUtil extends CollectionUtil {


    /**

     * <pre>
     * example:
     * orgin ArrayList<T>：[x,y,wangdi0410,test,name,abc]
     * stepLength = 2,return
     * [x,wangdi0410,name]
     * </pre>
     *
     * @param arrayList
     * @param stepLength
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> select(ArrayList<T> arrayList, int stepLength) {

        ArrayList<T> arrayListNew = new ArrayList<T>();
        if (CollectionUtil.isBlank(arrayList)) {
            return arrayListNew;
        }

        stepLength = IntegerUtil.defaultIfSmallerThan0(stepLength, 1);
        if (1 == stepLength)
            return arrayList;

        int index = 0;
        while (index + stepLength < arrayList.size()) {
            arrayListNew.add(arrayList.get(index));
            index += stepLength;
        }
        arrayListNew.add(arrayList.get(index));

        return arrayListNew;
    }


}
