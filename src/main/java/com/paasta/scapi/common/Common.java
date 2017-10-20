package com.paasta.scapi.common;

import sonia.scm.user.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author ijlee
 *         <p>
 *         create by 2017.06.21
 */
public class Common {

    /**
     * 날짜계산
     * @return string string
     */

	/*@Autowired
    public MessageSource messageSource;*/
    public static int diffDay(Date d, Date accessDate) {
        Calendar curC = Calendar.getInstance();
        Calendar accessC = Calendar.getInstance();
        curC.setTime(d);
        accessC.setTime(accessDate);
        accessC.compareTo(curC);
        int diffCnt = 0;
        while (!accessC.after(curC)) {
            diffCnt++;
            accessC.add(Calendar.DATE, 1); // 다음날로 바뀜
        }
        return diffCnt;
    }

    /**
     * 요청 파라미터들의 빈값 또는 null값 확인을 하나의 메소드로 처리할 수 있도록 생성한 메소드 요청 파라미터 중 빈값 또는
     * null값인 파라미터가 있는 경우, false를 리턴한다.
     *
     * @param params
     * @return
     */
    public static boolean stringNullCheck(String... params) {
        return Arrays.stream(params).allMatch(param -> null != param && !param.equals(""));
    }

    /**
     * Object type 변수가 비어있는지 체크
     *
     * @param obj
     * @return Boolean : true / false
     * Create by injeong
     */
    public static Boolean empty(Object obj) {
        if (obj instanceof String) {
            return "".equals(obj.toString().trim());
        } else if (obj instanceof List) {
            return ((List) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return Array.getLength(obj) == 0;
        } else {
            return obj == null;
        }
    }

    /**
     * Object type 변수가 비어있지 않은지 체크
     *
     * @param obj
     * @return Boolean : true / false
     */
    @org.jetbrains.annotations.Contract("null -> false")
    public static Boolean notEmpty(Object obj) {
        return !Common.empty(obj);
    }

    /**
     * Object type 변수가 비어있는지 확인하여 비어있으면 rtn값을 주고 비어있지 않으면 Object 를 보내준다.
     *
     * @param obj 확인할 변수
     * @param rtn 비어있으면 보내줄값
     *            Create by injeong
     */

    public static Object notNullrtnByobj(Object obj, Object rtn) {
        if (Common.empty(obj)) {
            return rtn;
        } else {
            return obj;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map rtnMapByRequestParam(HttpServletRequest request) {
        Map parameterMap = new HashMap();
        Enumeration enumeration = request.getParameterNames();
        if (enumeration == null) {
            return parameterMap;
        }
        while (enumeration.hasMoreElements()) {
            String paramName = (String) enumeration.nextElement();
            String[] parameters = request.getParameterValues(paramName);

            // Parameter가 배열일 경우
            if (parameters.length > 1) {
                List<Object> parmList = new ArrayList<>();

                for (String parameter : parameters) {
                    parmList.add(parmList.size(), parameter);
                }
                parameterMap.put(paramName, parmList);
                // Parameter가 배열이 아닌 경우
            } else {
                parameterMap.put(paramName, parameters[0]);
            }
        }

        return parameterMap;
    }

    /**
     * 사용자 이름, 사용자 list 를 가져와서 사용자 정보를 return해준다.
     * 전체 사용자 조회를 하여 사용자 정보 를 가져와야 할 경우 사용.
     */
    public static User getUserByUserName(String username, List<User> lstUser) {
        for (User aLstUser : lstUser) {
            if (username.equals(aLstUser.getName())) {
                return aLstUser;
            }
        }
        User emptyUser = new User();
        emptyUser.setActive(false);
        return emptyUser;
    }

    /**
     * 사용자 아이디 또는 이름 검색을 위한 List 필터
     */
    @SuppressWarnings("unchecked")
    public List<User> fillterUser(String username, List<User> list) {
        List rtnList = new ArrayList();
        list.forEach(user -> {
            if (Common.empty(username)) {
                list.add(user);
            }
            if (Common.notEmpty(username)) {
                if (user.getName().contains(username) || user.getDisplayName().contains(username)) {
                    list.add(user);
                }
            }
        });
        return rtnList;
    }

    @SuppressWarnings("unchecked")
    public static Map convertMapByLinkedHashMap(LinkedHashMap linkedHashMap) {
        Map map = new HashMap();
        if (!Common.empty(linkedHashMap)) {
            List<String> lstKey = new ArrayList(linkedHashMap.keySet());
            List<Object[]> lstValue = new ArrayList(linkedHashMap.values());
            for (int i = 0; i < lstKey.size(); i++) {
                map.put(lstKey.get(i), lstValue.get(i));
            }

        }
        return map;
    }

}
