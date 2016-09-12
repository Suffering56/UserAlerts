package gui.panel.userAlerts.data.remote;

import gui.panel.userAlerts.App;
import gui.panel.userAlerts.data.NewsProperties;
import gui.panel.userAlerts.overridden.model.NewsTreeNode;
import gui.panel.userAlerts.overridden.model.NewsTreeNodeAbstract.NodeType;
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

public abstract class RemoteBasicAPI {

	public RemoteBasicAPI(Stock stock) {
		this.stock = stock;

		initInterfaces();

		pTNewsClientAPI = new PTNewsClientAPI(newsListener);
		pTNewsClientAPI.getNewsDBConfiguration(newsConfigListener);
		connectionClientAPI = new ConnectionClientAPI(networkListener);

		try {
			connect();
		} catch (BadParametersException e) {
			e.printStackTrace();
		}
	}

	private void connect() throws BadParametersException {
		connectionClientAPI.assignNewConnectCredentials("orenburg", "123", "moon.1prime.ru", TYPE.INFO, 6015, 30000, 30000, 100);
		connectionClientAPI.assignSink(pTNewsClientAPI.getNetworkInterface());
	}

	private void initInterfaces() {
		networkListener = new INetworkStatusInterface() {
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

		newsListener = new INewsStatusInterface() {
			public void currentServerDoesNotSupportRequiredNewsVersion() {
				App.appLogger.error("currentServerDoesNotSupportRequiredNewsVersion");
			}
		};

		newsConfigListener = new INewsConfigListener() {
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
		initAdditionalInterfaces();
	}

	abstract protected void initAdditionalInterfaces();

	/**
	 * Загружаем дерево категорий новостей.
	 */
	private void createTreeNode(byte[] config) {
		NewsDatabase[] nd = pTNewsClientAPI.getParseNewsConfig(config);
		NewsTreeNode root = new NewsTreeNode(NodeType.ROOT, "Новости");

		for (int j = 0; j < nd.length; j++) {
			NewsDatabase db = nd[j];
			if (!NewsProperties.getInstance().getExcludeNewsSet().contains(db.getCommonName())) {
				databaseHandle(db, root);
			}
		}

		stock.setNewsRoot(root);
	}

	/**
	 * Обработка раздела новостей.
	 */
	private void databaseHandle(NewsDatabase db, NewsTreeNode root) {
		NewsTreeNode database;
		NewsTreeNode division;
		NewsTreeNode topic;

		database = new NewsTreeNode(NodeType.DATABASE, db.getCommonName(), db.getName());
		Div[] dm = db.getDivs();
		for (int i = 0; i < dm.length; i++) {
			division = new NewsTreeNode(NodeType.DIVISION, dm[i].getName());

			for (Pair<Integer, String> p : dm[i].getTopics()) {
				int id = Integer.valueOf("" + p.fst);
				topic = new NewsTreeNode(NodeType.TOPIC, p.snd, id);
				division.add(topic);
			}

			database.add(division);
		}
		root.add(database);
	}

	protected final Stock stock;
	protected ConnectionClientAPI connectionClientAPI;

	private final PTNewsClientAPI pTNewsClientAPI;
	private INetworkStatusInterface networkListener;
	private INewsStatusInterface newsListener;
	private INewsConfigListener newsConfigListener;
}
