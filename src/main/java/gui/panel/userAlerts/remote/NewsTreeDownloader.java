package gui.panel.userAlerts.remote;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.Stock;
import gui.panel.userAlerts.overridden.model.CheckableTreeNode;
import gui.panel.userAlerts.overridden.model.PTNewsTreeNode;
import gui.panel.userAlerts.overridden.model.PTNewsTreeNode.NodeType;
import prime_tass.connect.BadParametersException;
import prime_tass.connect.client_api.ConnectionClientAPI;
import prime_tass.connect.client_api.ConnectionClientAPI.TYPE;
import prime_tass.connect.client_api.interf.INetworkStatusInterface;
import pt.news.client_api.PTNewsClientAPI;
import pt.news.client_api.assets.Pair;
import pt.news.client_api.assets.xml.Div;
import pt.news.client_api.assets.xml.NewsDatabase;
import pt.news.client_api.interf.INewsConfigListener;
import pt.news.client_api.interf.INewsStatusInterface;

public class NewsTreeDownloader {

	public NewsTreeDownloader(Stock stock) {
		this.stock = stock;

		initStatusInterfaces();

		pTNewsClientAPI = new PTNewsClientAPI(iNewsStatusInterface);

		INewsConfigListener iNewsConfigListener = new INewsConfigListener() {
			public void failedToAquireConfiguration() {
				App.appLogger.error("failedToAquireConfiguration");
			}

			public void downloadingConfiguration() {
				// App.appLogger.info("downloading Configuration");
			}

			public void configurationUpdate(byte[] config) {
				createTreeNode(config);
			}
		};

		pTNewsClientAPI.getNewsDBConfiguration(iNewsConfigListener);

		ConnectionClientAPI cc = new ConnectionClientAPI(iNetworkStatusInterface);

		try {
			cc.assignNewConnectCredentials("guest2", "guest", "moon.1prime.ru", TYPE.INFO, 6013, 30000, 30000, 100);
		} catch (BadParametersException e) {
			e.printStackTrace();
		}

		cc.assignSink(pTNewsClientAPI.getNetworkInterface());
	}

	private void createTreeNode(byte[] config) {
		NewsDatabase[] nd = pTNewsClientAPI.getParseNewsConfig(config);

		PTNewsTreeNode root = new PTNewsTreeNode(NodeType.ROOT, "Новости");
		PTNewsTreeNode database;
		PTNewsTreeNode division;
		PTNewsTreeNode topic;

		for (int j = 0; j < nd.length; j++) {
			NewsDatabase db = nd[j];
			database = new PTNewsTreeNode(NodeType.DATABASE, db.getCommonName());
			Div[] dm = db.getDivs();
			for (int i = 0; i < dm.length; i++) {
				division = new PTNewsTreeNode(NodeType.DIVISION, dm[i].getName());

				for (Pair<Integer, String> p : dm[i].getTopics()) {
					System.out.println(p.fst);
//					topic = new PTNewsTreeNode(NodeType.TOPIC, p.snd, p.fst);
//					division.add(topic);
				}

				database.add(division);
			}

			root.add(database);
		}

		stock.updateNewsTree(root);
	}

	private void initStatusInterfaces() {
		iNewsStatusInterface = new INewsStatusInterface() {
			public void currentServerDoesNotSupportRequiredNewsVersion() {
				App.appLogger.error("currentServerDoesNotSupportRequiredNewsVersion");
			}
		};

		iNetworkStatusInterface = new INetworkStatusInterface() {
			public void connectionEstablished() {
				App.appLogger.info("connectionEstablished");
			}

			public void networkActivity() {
				// App.appLogger.info("networkActivity");
			}

			public void wrongCredentials() {
				App.appLogger.info("wrongCredentials");
			}

			public void disconnectedFromServer() {
				App.appLogger.info("disconnectedFromServer");
			}

			public void serverAcceptedCredentials() {
			}

			@Override
			public void serverRefusedConnection(String arg0) {
				// TODO Auto-generated method stub
			}
		};
	}

	private final Stock stock;
	private INewsStatusInterface iNewsStatusInterface;
	private INetworkStatusInterface iNetworkStatusInterface;
	private final PTNewsClientAPI pTNewsClientAPI;
}
