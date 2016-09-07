import p.alerts.client_api.AlertsSMSandMailAPI;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.QuoteAlert;
import prime_tass.connect.client_api.interf.INetworkStatusInterface;
import prime_tass.connect.client_api.ConnectionClientAPI.TYPE;
import prime_tass.connect.client_api.ConnectionClientAPI;
import p.alerts.client_api.interf.IAlertEventsCallBack;
import p.alerts.client_api.interf.ILoginCallbackListener;
import p.alerts.client_api.interf.ILogoutCallBackListener;

// cc.assignNewConnectCredentials("guest2", "guest", "moon.1prime.ru", TYPE.INFO, 6013, 30000, 30000, 100);
/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2016
 * </p>
 *
 * <p>
 * Company: Prime
 * </p>
 *
 * @author Timofey
 * @version 1.0
 */
public class Test {

	public Test() {
	}

	public static void main(String[] args) throws Exception {
		INetworkStatusInterface interf1 = new INetworkStatusInterface() {
			public void connectionEstablished() {
				System.out.println("Connection Established");
			}

			public void networkActivity() {
				// System.out.println("Network Activity");
			}

			public void serverRefusedConnection(String reason) {
				System.out.println("server Refused Connection " + reason);
			}

			public void wrongCredentials() {
				System.out.println("Wrong credentials");
			}

			public void disconnectedFromServer() {
				System.out.println("Disconnected From Server");
			}

			public void serverAcceptedCredentials() {
				System.out.println("server Accepted Credentials");
			}
		};

		////////////////////////////////////////////
		//  оннекционна¤ библиотека инициализаци¤ //
		ConnectionClientAPI cc = new ConnectionClientAPI(interf1);
		cc.assignNewConnectCredentials("orenburg", "123", "moon.1prime.ru", TYPE.INFO, 6015, 30000, 30000, 100);

		///////////////////////////////////////////
		// инициализаци¤ API //
		final AlertsSMSandMailAPI api = new AlertsSMSandMailAPI();
		cc.assignSink(api.getNetworkInterface());

		//////////////////////////////////////////////
		// login зарегестрированного пользовател¤. ///
		ILoginCallbackListener cb = new ILoginCallbackListener() {
			public void loginSuccessful() {
				System.out.println("loginSuccessful()");
				IAlertEventsCallBack iAlertEventsCallBack = new IAlertEventsCallBack() {

					@Override
					public void falureOccured(String arg0) {
						System.out.println("falureOccured: " + arg0);
					}

					@Override
					public void eventOccured(NewsAlert[] arg0, QuoteAlert[] arg1) {
						System.out.println("eventOccured");
					}
				};

				api.subscribeToAlertEvents(iAlertEventsCallBack);
			}

			public void loginOperationFailed(String reason) {
				System.out.println("login Operation Failed =" + reason);
			}
		};

		api.login("doom", "doom_pass", cb);

		Thread.sleep(4000);
		/////////////////////////////////////
		// LOGOUT SESSION

		ILogoutCallBackListener cb_logout = new ILogoutCallBackListener() {
			public void logoutSuccessful() {
				System.out.println("logout Successful");
			}

			public void logoutFailed(String reason) {
				System.out.println("logout Failed =" + reason);
			}
		};

		api.logout(cb_logout);

	}
}
