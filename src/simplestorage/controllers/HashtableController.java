/*
Copyright (C) 2009 Jiangyan Xu <jiangyan@ufl.edu>, University of Florida
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
*/

package simplestorage.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import simplestorage.hashtable.HashtableGetResult;
import simplestorage.hashtable.HashtableService;

import com.google.gson.Gson;

/**
 * @author Jiangyan Xu <jiangyan@ufl.edu>
 * 
 */
@Controller
public class HashtableController {
  private Gson gson;
  private HashtableService hashtableSvc;
  public static final String JSON_MODEL = "responseJson";
  public static final String JSON_VIEW = "HashtableJson";
  
  public HashtableController() {
    gson = new Gson();
    hashtableSvc = new HashtableService();
  }
  
  @RequestMapping(value = "/hashtable/{key}", method = RequestMethod.GET)
  public ModelAndView get(HttpServletRequest request, HttpServletResponse response, 
      @PathVariable String key) {
    HashtableGetResult result = hashtableSvc.get(key);
    return constructModelAndView(gson.toJson(result));
  }
  
  @RequestMapping(value = "/hashtable/{key}", method = RequestMethod.POST)
  public ModelAndView put(HttpServletRequest request, HttpServletResponse response, 
      @PathVariable String key) throws IOException {
    // If I can't read from input stream, there is nothing I can do about it.
    InputStream input = request.getInputStream();
    byte[] value = new byte[request.getContentLength()];
    input.read(value, 0, request.getContentLength());
    String userInfo = request.getRemoteAddr();
    hashtableSvc.put(key, value, userInfo);
    return constructModelAndView(gson.toJson(Boolean.TRUE));
  }
  
  @RequestMapping(value = "/hashtable/{key}/{value}")
  public ModelAndView put(HttpServletRequest request, HttpServletResponse response, 
      @PathVariable String key, @PathVariable String value) throws IOException {
    String userInfo = request.getRemoteAddr();
    byte[] valueBytes = value.getBytes("UTF-8");
    hashtableSvc.put(key, valueBytes, userInfo);
    return constructModelAndView(gson.toJson(Boolean.TRUE));
  }
  
  /**
   * Creates a Spring ModelAndView representation from the JSON formatted
   * result.
   *
   * @param json the data model in MVC.
   * @return ModelAndView a Spring MVC framework instance with data and view
   *         for the client
   */
  private static ModelAndView constructModelAndView(String json) {
    ModelAndView mv = new ModelAndView();
    mv.addObject(JSON_MODEL, json);
    mv.setViewName(JSON_VIEW);
    return mv;
  }
}