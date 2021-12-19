/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.security;
import gplx.*; import gplx.core.*;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
public class Security_utl_ {
	private static final void List_algos(Provider prov, Class<?> typeClass) {
		String type = typeClass.getSimpleName();

		List<Service> algos = new ArrayList<Service>();

		Set<Service> services = prov.getServices();
		for (Service service : services) {
			if (service.getType().equalsIgnoreCase(type)) {
				algos.add(service);
			}
		}

		if (!algos.isEmpty()) {
			System.out.printf(" --- Provider %s, version %.2f --- %n", prov.getName(), prov.getVersion());
			for (Service service : algos) {
				String algo = service.getAlgorithm();
				System.out.printf("Algorithm name: \"%s\"%n", algo);


			}
		}

		// --- find aliases (inefficiently)
		Set<Object> keys = prov.keySet();
		for (Object key : keys) {
			final String prefix = "Alg.Alias." + type + ".";
			if (key.toString().startsWith(prefix)) {
				String value = prov.get(key.toString()).toString();
				System.out.printf("Alias: \"%s\" -> \"%s\"%n",
						key.toString().substring(prefix.length()),
						value);
			}
		}
	}

	public static void main(String[] args) {
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers) {
			List_algos(provider, MessageDigest.class);
		}
	}
}
