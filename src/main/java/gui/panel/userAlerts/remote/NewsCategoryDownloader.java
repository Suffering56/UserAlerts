package gui.panel.userAlerts.remote;

import javax.swing.tree.DefaultMutableTreeNode;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.Stock;
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

public class NewsCategoryDownloader {

	public NewsCategoryDownloader(Stock stock) {
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
				// App.appLogger.info("Successfuly arrived news database!");
				createTreeNode(config);
				// x(config);
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

		newsTreeRoot = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode section;
		DefaultMutableTreeNode division;
		DefaultMutableTreeNode topic;

		for (int j = 0; j < nd.length; j++) {
			NewsDatabase db = nd[j];
			section = new DefaultMutableTreeNode(db.getCommonName());

			Div[] dm = db.getDivs();
			for (int i = 0; i < dm.length; i++) {
				division = new DefaultMutableTreeNode(dm[i].getName());

				for (Pair<Integer, String> p : dm[i].getTopics()) {
					topic = new DefaultMutableTreeNode(p.snd);
					division.add(topic);
				}
				
				section.add(division);
			}

			newsTreeRoot.add(section);
		}

		stock.updateTree(newsTreeRoot);
	}

	void x(byte[] config) {
		System.out.println("Successfuly arrived news database!");

		// now parsing
		NewsDatabase[] nd = pTNewsClientAPI.getParseNewsConfig(config);

		System.out.println("loaded " + nd.length + " news databases.");

		NewsDatabase db = nd[0];
		System.out.println("database 0:\r\nname=" + db.getName());
		System.out.println("common name=" + db.getCommonName());
		System.out.println("database have " + db.getDivsAmmount() + " divisions ammount");

		Div[] dm = db.getDivs();
		for (int i = 0; i < dm.length; i++) {
			System.out.println("    Division[" + i + "] name=" + dm[i].getName() + " contains "
					+ dm[i].getTopicsAmmount() + " topics:");

			for (Pair<Integer, String> p : dm[i].getTopics()) {
				System.out.println("         topic id=" + p.fst + "    name=" + p.snd);
			}
		}

		System.out.println("database have " + db.getDirsAmmount() + " directions");
		for (Pair<Integer, String> p : db.getDirs()) {
			System.out.println("    direction id=" + p.fst + "    name=" + p.snd);
		}
	}

	private void initStatusInterfaces() {
		iNewsStatusInterface = new INewsStatusInterface() {
			public void currentServerDoesNotSupportRequiredNewsVersion() {
				App.appLogger.error("currentServerDoesNotSupportRequiredNewsVersion");
			}
		};

		iNetworkStatusInterface = new INetworkStatusInterface() {
			public void connectionEstablished() {
				System.out.println("connectionEstablished");
			}

			public void networkActivity() {
				// System.out.println("networkActivity");
			}

			public void wrongCredentials() {
				System.out.println("wrongCredentials");
			}

			public void disconnectedFromServer() {
				System.out.println("disconnectedFromServer");
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
	private DefaultMutableTreeNode newsTreeRoot = new DefaultMutableTreeNode("root");

	private INewsStatusInterface iNewsStatusInterface;
	private INetworkStatusInterface iNetworkStatusInterface;
	private final PTNewsClientAPI pTNewsClientAPI;
}
