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
package com.cloudera.access.core;

import java.util.EnumSet;


public interface AuthorizationProvider {

  /**
   * Returns true if the user has the requested privileges on the server, database, and table.
   */
  public boolean hasAccess(Subject subject, Server server, Database database, Table table, EnumSet<Privilege> privileges);

  /**
   * Returns true if the subject has the requested privileges on the server i.e. the
   * subject has this privilege server wide.
   */
  public boolean hasAccess(Subject subject, Server server, ServerResource serverResource, EnumSet<Privilege> privileges);

}