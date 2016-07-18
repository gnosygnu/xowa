/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.core.threads.utils; import gplx.*; import gplx.core.*; import gplx.core.threads.*;
import java.util.concurrent.CountDownLatch;
public class Gfo_countdown_latch {
		private final CountDownLatch latch;
		public Gfo_countdown_latch(int count) {
				latch = new CountDownLatch(count);
			}
	public void Countdown() {
				latch.countDown();
			}
	public void Await() {
				try {latch.await();}
		catch (InterruptedException e) {throw Err_.new_exc(e, "threads", "await interrupted");}
			}
}
