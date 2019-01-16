/**
 * Mac.java
 *
 * Copyright 2015-2019 Heartland Software Solutions Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the license at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the LIcense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.hss.platform;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Mac extends OperatingSystem {

	Mac() { }
	
	@Override
	public Type getType() {
		return Type.Mac;
	}
	
	@Override
	public Long getPid(Process process) {
		Long pid = null;

		//Java 9+
		try {
			Method method = process.getClass().getMethod("pid");
			if (method != null) {
				Object o = method.invoke(process);
				if (o instanceof Number)
					pid = ((Number)o).longValue();
			}
		}
		catch (Exception e) { }
		//platform specific
		if (pid == null) {
			try {
				if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
					Field field = process.getClass().getDeclaredField("pid");
					field.setAccessible(true);
					pid = field.getLong(process);
					field.setAccessible(false);
				}
			}
			catch (Exception e) {}
		}
		
		return pid;
	}
}