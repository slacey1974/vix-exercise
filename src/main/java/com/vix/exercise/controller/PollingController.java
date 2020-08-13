package com.vix.exercise.controller;

import com.vix.exercise.service.impl.PollingResource;
import com.vix.exercise.service.impl.Polling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.core.Response;

import java.util.List;

@RestController
@RequestMapping("/polling")
public class PollingController {

      private static final String POLL_URL = "/polling/";

      private static final String POLL_LIST_DETAILS_VIEW = "polling-record-form";
      private static final String POLL_LIST_DETAILS_URL = "/" + POLL_LIST_DETAILS_VIEW;

      private static final String POLL_UPDATE_PAGE_VIEW = "update-poll-page";
      private static final String POLL_UPDATE_PAGE_URL = "/" + POLL_UPDATE_PAGE_VIEW;
      private static final String POLL_UPDATE_DETAILS_VIEW = "update-poll-details";
      private static final String POLL_UPDATE_DETAILS_URL = "/" + POLL_UPDATE_DETAILS_VIEW;

      private static final String POLL_ADD_PAGE_VIEW = "new-poll-page";
      private static final String POLL_ADD_PAGE_URL = "/" + POLL_ADD_PAGE_VIEW;
      private static final String POLL_ADD_DETAILS_VIEW = "new-poll-details";
      private static final String POLL_ADD_DETAILS_URL = "/" + POLL_ADD_DETAILS_VIEW;

      private static final String POLL_DELETE_PAGE_VIEW = "delete-poll-page";
      private static final String POLL_DELETE_PAGE_URL = "/" + POLL_DELETE_PAGE_VIEW;
      private static final String POLL_DELETE_DETAILS_VIEW = "delete-poll-details";
      private static final String POLL_DELETE_DETAILS_URL = "/" + POLL_DELETE_DETAILS_VIEW;

      private static final String POLLING_VIEW = "polling";
      private static final String POLLING_URL = "/" + POLLING_VIEW;

      private static final String POLL_SUCCESS = "/success";
      private static final String POLL_UNSUCCESS = "/unsuccess";

      @Autowired
      private PollingResource service;

      public PollingController(PollingResource service) {
        this.service = service;
      }

      @GetMapping(POLL_LIST_DETAILS_URL)
      public ModelAndView viewPage() {
        Response response =  service.getPollingRecordList();
        if (response.getStatus() == 200) {
          ModelAndView model = new ModelAndView(POLL_URL + POLL_LIST_DETAILS_VIEW);
          List<Polling> pollingList = (List<Polling>)response.getEntity();

          model.addObject("records", pollingList);
          return model;

        } else {
          return new ModelAndView(doRedirect(POLL_UNSUCCESS));
        }
      }

      @GetMapping(POLL_UPDATE_PAGE_URL)
      public ModelAndView viewUpdatePage() {

          Response response = service.getPollingRecordList();
          if (response.getStatus() == 200) {
              ModelAndView model = new ModelAndView(POLL_URL + POLL_LIST_DETAILS_VIEW);
              List<Polling> pollingList = (List<Polling>) response.getEntity();

              model.addObject("records", pollingList);
              return model;

          } else {
              return new ModelAndView(doRedirect(POLL_UNSUCCESS));
          }

      }

      @GetMapping(POLL_ADD_PAGE_URL)
      public ModelAndView viewInsertPage() {
        return new ModelAndView(POLL_URL + POLL_ADD_PAGE_VIEW);
      }

      @GetMapping(POLL_DELETE_PAGE_URL)
      public ModelAndView viewDeletePage() {
          Response response =  service.getPollingRecordList();
          if (response.getStatus() == 200) {
              ModelAndView model = new ModelAndView(POLL_URL + POLL_DELETE_DETAILS_VIEW);
              List<Polling> pollingList = (List<Polling>)response.getEntity();

              model.addObject("records", pollingList);
              return model;

          } else {
              return new ModelAndView(doRedirect(POLL_UNSUCCESS));
          }
      }

      @PostMapping(POLL_ADD_DETAILS_URL)
      public String submitNewRecord(@RequestAttribute String status) {
        Response response = service.createPollingRecord(status);
        if (response.getStatus() == 200) {
          return doRedirect(POLL_SUCCESS);
        } else {
          return doRedirect(POLL_UNSUCCESS);
        }
      }

      @PostMapping(POLL_UPDATE_DETAILS_URL)
      public String submitUpdateRecord(@PathVariable("id") int id, @RequestAttribute String status) {
        Response response = service.updatePollingRecord(id, status);
        if (response.getStatus() == 200) {
          return doRedirect(POLL_SUCCESS);
        } else {
          return doRedirect(POLL_UNSUCCESS);
        }
      }

      @PostMapping(POLLING_URL)
      public String poll() {
          Response response = service.poll("http://vix.digital");
          if (response.getStatus() == 200) {
              return doRedirect(POLL_SUCCESS);
          } else {
              return doRedirect(POLL_UNSUCCESS);
          }
      }


      @GetMapping(POLL_DELETE_DETAILS_URL)
      public String submitDeleteUpdateRecord(@PathVariable("id") int id) {
        Response response = service.delete(id);
        if (response.getStatus() == 200) {
          return doRedirect(POLL_SUCCESS);
        } else {
          return doRedirect(POLL_UNSUCCESS);
        }
      }

      protected String doRedirect(String url) {
        return "redirect:" + url;
      }


}
