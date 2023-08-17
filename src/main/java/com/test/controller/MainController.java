package com.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.test.cron.QuartzScheduler;
import com.test.dao.FiddlerTemplateRepository;
import com.test.entities.FiddlerTemplate;
import com.test.entities.Persons;

import com.test.util.HttpUtil;
import com.test.controller.pagination.PaginationFormatting;
import com.test.controller.pagination.PaginationMultiTypeValuesHelper;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.PageRequest;

import com.test.dao.PersonsRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


@RestController
@RequestMapping("/api/persons")
public class MainController {

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private FiddlerTemplateRepository fiddlerTemplateRepository;

    @Autowired
    private QuartzScheduler quartzScheduler;

    @Value(("${com.boylegu.paginatio.max-per-page}"))
    Integer maxPerPage;

    @RequestMapping(value = "/sex", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSexAll() {

        /*
         * @api {GET} /api/persons/sex Get all sexList
         * @apiName GetAllSexList
         * @apiGroup Info Manage
         * @apiVersion 1.0.0
         * @apiExample {httpie} Example usage:
         *
         *     http /api/persons/sex
         *
         * @apiSuccess {String} label
         * @apiSuccess {String} value
         */

        ArrayList<Map<String, String>> results = new ArrayList<>();

        for (Object value : personsRepository.findSex()) {

            Map<String, String> sex = new HashMap<>();

            sex.put("label", value.toString());

            sex.put("value", value.toString());

            results.add(sex);
        }

        ResponseEntity<ArrayList<Map<String, String>>> responseEntity = new ResponseEntity<>(results,
                HttpStatus.OK);

        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, PaginationMultiTypeValuesHelper> getPersonsAll(
            @RequestParam(value = "page", required = false) Integer pages,
            @RequestParam("sex") String sex,
            @RequestParam("email") String email
    ) {

        /*
         *   @api {GET} /api/persons   Get all or a part of person info
         *   @apiName GetAllInfoList
         *   @apiGroup Info Manage
         *   @apiVersion 1.0.0
         *
         *   @apiExample {httpie} Example usage: (support combinatorial search)
         *
         *       All person：
         *       http /api/persons
         *
         *       You can according to 'sex | email' or 'sex & email'
         *       http /api/persons?sex=xxx&email=xx
         *       http /api/persons?sex=xxx
         *       http /api/persons?email=xx
         *
         *   @apiParam {String} sex
         *   @apiParam {String} email
         *
         *   @apiSuccess {String} create_datetime
         *   @apiSuccess {String} email
         *   @apiSuccess {String} id
         *   @apiSuccess {String} phone
         *   @apiSuccess {String} sex
         *   @apiSuccess {String} username
         *   @apiSuccess {String} zone
         */

        if (pages == null) {

            pages = 1;

        }

        Sort sort = new Sort(Direction.ASC, "id");

        Pageable pageable = new PageRequest(pages - 1, maxPerPage, sort);

        PaginationFormatting paginInstance = new PaginationFormatting();

        return paginInstance.filterQuery(sex, email, pageable);
    }


    @RequestMapping(value = "/testPlan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FiddlerTemplate> getTestCaseData() {
        return fiddlerTemplateRepository.findAll();
    }

    @RequestMapping(value = "/cron", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean getCronData(@RequestBody String data) {

        return null;
    }

    @RequestMapping(value = "/excel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FiddlerTemplate> getExcelDetail(@RequestBody String data) {
        FiddlerTemplate ft = null;
        String expectedString1 = "\"code\":100";
        String expectedString2 = "成功";
        data = data.substring(8, data.length() - 1);
        ArrayList<FiddlerTemplate> fts = JSON.parseObject(data, new TypeReference<ArrayList<FiddlerTemplate>>() {
        });
        ArrayList<FiddlerTemplate> newList = new ArrayList<FiddlerTemplate>();
        String responseData = "";
        for (FiddlerTemplate ft2 : fts) {
            ft = ft2;
            //System.out.println(ft.getUrl()+":"+ft.getHead());
            ft.setFullUrl(ft.getProtocol() + "://" + ft.getHost() + ft.getUrl());
            if (ft.getRequestMethod().equals("GET")) {
                responseData = HttpUtil.doGet(ft.getFullUrl(), ft.getContentType(), "");
            } else if (ft.getRequestMethod().equals("POST")) {
                responseData = HttpUtil.doPost(ft.getFullUrl(), ft.getContentType(), "",
                        ft.getHead());
            } else if (ft.getRequestMethod().equals("PUT")) {
                responseData = HttpUtil.doPut(ft.getFullUrl(), ft.getContentType(), "",
                        ft.getHead());
            } else if (ft.getRequestMethod().equals("DELETE")) {
                responseData = HttpUtil.doDelete(ft.getFullUrl(), ft.getContentType(), "",
                        ft.getHead());
            } else {
                System.out.println("TODO method");
            }
            ft.setResponseData(responseData);
            //System.out.println("responseData:"+responseData);
            if (responseData != null) {
                if (responseData.contains(expectedString1) || responseData.contains(expectedString2)) {
                    ft.setFinalTestResult("测试通过");
                } else {
                    ft.setFinalTestResult("测试不通过");
                }
            }
            newList.add(ft);
        }
        return newList;
    }

    @RequestMapping(value = "/insertDB", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public FiddlerTemplate updateExcelToDB(@RequestBody String data) {
        FiddlerTemplate ft = null;
        data = data.substring(8, data.length() - 1);
        ArrayList<FiddlerTemplate> fts = JSON.parseObject(data, new TypeReference<ArrayList<FiddlerTemplate>>() {
        });
        ArrayList<FiddlerTemplate> newList = new ArrayList<FiddlerTemplate>();
        for (FiddlerTemplate ft2 : fts) {
            ft2.setCaseId(String.valueOf(ft2.getId()));
            ft2.setId(0);
            ft2.setFullUrl(ft2.getProtocol() + "://" + ft2.getHost() + ft2.getUrl());
            //System.out.println("ft2.getRequestMethod():"+ft2.getRequestMethod());
            if (fiddlerTemplateRepository.findByRequestMethodAndFullUrlAndHeadAndResponseData(ft2.getRequestMethod(), ft2.getFullUrl(), ft2.getHead(), ft2.getResponseData()) == null) {
                fiddlerTemplateRepository.save(ft2);
            }
        }
        return null;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Persons> getUserDetail(@PathVariable Long id) {

        /*
         *    @api {GET} /api/persons/detail/:id  details info
         *    @apiName GetPersonDetails
         *    @apiGroup Info Manage
         *    @apiVersion 1.0.0
         *
         *    @apiExample {httpie} Example usage:
         *
         *        http GET http://127.0.0.1:8000/api/persons/detail/1
         *
         *    @apiSuccess {String} email
         *    @apiSuccess {String} id
         *    @apiSuccess {String} phone
         *    @apiSuccess {String} sex
         *    @apiSuccess {String} username
         *    @apiSuccess {String} zone
         */

        Persons user = personsRepository.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Persons updateUser(@PathVariable Long id, @RequestBody Persons data) {

        /*
         *  @api {PUT} /api/persons/detail/:id  update person info
         *  @apiName PutPersonDetails
         *  @apiGroup Info Manage
         *  @apiVersion 1.0.0
         *
         *  @apiParam {String} phone
         *  @apiParam {String} zone
         *
         *  @apiSuccess {String} create_datetime
         *  @apiSuccess {String} email
         *  @apiSuccess {String} id
         *  @apiSuccess {String} phone
         *  @apiSuccess {String} sex
         *  @apiSuccess {String} username
         *  @apiSuccess {String} zone

         */
        Persons user = personsRepository.findById(id);

        user.setPhone(data.getPhone());

        try {
            user.setZone(URLDecoder.decode(data.getZone(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return personsRepository.save(user);
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void startQuartzJob(@RequestBody String data) {
        try {
            quartzScheduler.startJob((data.split("\\:")[1]).replace("\"",""));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getQuartzJob() {
        String info = null;
        try {
            info = quartzScheduler.getJobInfo("job1", "group1");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return info;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean modifyQuartzJob(@RequestBody String data) {
        boolean flag = true;
        try {
            //flag = quartzScheduler.modifyJob(name, group, time);
            flag = quartzScheduler.modifyJob("job1", "group1", (data.split("\\:")[1]).replace("\"",""));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/pause")
    public void pauseQuartzJob(String name, String group) {
        try {
            quartzScheduler.pauseJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/pauseAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void pauseAllQuartzJob() {
        try {
            quartzScheduler.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void resumeQuartzJob() {
        try {
            quartzScheduler.resumeJob("job1", "group1");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteJob() {
        try {
            //quartzScheduler.deleteJob(name, group);
            quartzScheduler.deleteJob("job1", "group1");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}