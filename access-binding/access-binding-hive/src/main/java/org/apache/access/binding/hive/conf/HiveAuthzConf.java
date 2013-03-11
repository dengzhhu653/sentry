/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.access.binding.hive.conf;

import java.net.URL;

import org.apache.access.binding.hive.authz.HiveAuthzBinding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;


public class HiveAuthzConf extends Configuration {
  /**
   * Config setting definitions
   */
  public static enum AuthzConfVars {
    AUTHZ_PROVIDER("hive.authz.provider",
        "com.cloudera.access.provider.file.ResourceAuthorizationProvider"),
    AUTHZ_PROVIDERRES_RESOURCE("hive.authz.provider.resource", ""),
    AUTHZ_SERVER_NAME("hive.authz.server", "HS2")
   ;

   private final String varName;
   private final String defaultVal;

   AuthzConfVars(String varName, String defaultVal) {
     this.varName = varName;
     this.defaultVal = defaultVal;
   }
   
   public String getVar() {
     return varName;
   }
   
   public String getDefault(String varName) {
     return valueOf(varName).defaultVal;
   }
  }

  static final private Log LOG = LogFactory.getLog(HiveAuthzConf.class.getName());
  public static String AUTHZ_SITE_FILE = "authz-site.xml";
  private static URL hiveAuthzSiteURL;
  
  public HiveAuthzConf() {
    super(false);
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    if (classLoader == null) {
      classLoader = HiveAuthzConf.class.getClassLoader();
    }

    // Look for hive-site.xml on the CLASSPATH and log its location if found.
    hiveAuthzSiteURL = classLoader.getResource(AUTHZ_SITE_FILE);
    if (hiveAuthzSiteURL == null) {
      LOG.warn("Authz site file " + AUTHZ_SITE_FILE + " not found");
    } else {
      addResource(hiveAuthzSiteURL);
    }
  }

  @Override
  public String get(String varName) {
    String retVal = super.get(varName);
    if (retVal == null) {
      retVal = AuthzConfVars.valueOf(varName).getDefault(varName);
    }
    return retVal;
  }

  /**
   * @return the authzSiteURL
   */
  public static URL getAuthzSiteURL() {
    return hiveAuthzSiteURL;
  }

}
