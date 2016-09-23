package gui.panel.userAlerts.data;

import java.awt.Color;

import gui.panel.userAlerts.util.StringHelper;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NewsAlert.NEWS_WORDS_EXPRESSION;
import p.alerts.client_api.NewsAlert.SEARCH_NEWS_TYPE;

public class ClientNewsAlert extends ClientAlert {

	public ClientNewsAlert() {
		this(null, true, false, ETERNITY_LIFETIME, true, null, false, null, null, null, null, null, null, null, false, null, false, null, false, null,
				null, true);
	}

	public ClientNewsAlert(String name, boolean statusOn, boolean afterTriggerRemove, int lifetime, boolean keepHistory, String newsLine,
			boolean onlyRedNewsOn, String firstKeyWord, String secondKeyWord, Expression keyWordExpression, String firstExcludeWord,
			String secondExcludeWord, Expression excludeWordExpression, SEARCH_NEWS_TYPE everywhereFilterType, boolean emailOn, String email,
			boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody, Color newsColor, boolean notifyWindowOn) {

		super(name, statusOn, afterTriggerRemove, keepHistory, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		setLifetime(lifetime);

		setNewsLine(newsLine);
		setOnlyRedNewsOn(onlyRedNewsOn);

		setFirstKeyWord(firstKeyWord);
		setSecondKeyWord(secondKeyWord);
		setKeyWordExpression(keyWordExpression);

		setFirstExcludeWord(firstExcludeWord);
		setSecondExcludeWord(secondExcludeWord);
		setExcludeWordExpression(excludeWordExpression);

		setEverywhereFilterType(everywhereFilterType);

		setNewsColor(newsColor);
	}

	public ClientNewsAlert(NewsAlert serverAlert) {
		setCreationDate(serverAlert.getCreationDate());
		setLastEventDate(serverAlert.getLastFireEventDate());

		setId(serverAlert.getAlertID());
		setName(serverAlert.getName());

		setKeepHistory(serverAlert.getKeepHistory());
		setAfterTriggerRemove(serverAlert.getRemoveAfterFireUp());
		setLifetime(serverAlert.getCloseAlertAfterFireups());
		setStatusOn(!serverAlert.getDisabledAlert());

		setEmail(serverAlert.getEmail().equals(NONE) ? StringHelper.EMPTY : serverAlert.getEmail());
		setEmailOn(!email.isEmpty());
		setPhoneSms(serverAlert.getPhone().equals(NONE) ? StringHelper.EMPTY : serverAlert.getPhone());
		setPhoneSmsOn(!phoneSms.isEmpty());
		setMelody(serverAlert.getSoundSetup());
		setMelodyOn(!melody.isEmpty());
		setPopupWindowOn(serverAlert.getPopupWindow());

		setNewsLine(serverAlert.getPTNewsBases());
		setOnlyRedNewsOn(serverAlert.getUseImportantOnly());
		setNewsColor(serverAlert.getColor());

		setFirstKeyWord(serverAlert.getSearchWord(0));
		setSecondKeyWord(serverAlert.getSearchWord(1));
		setKeyWordExpression(convert_NEWS_WORDS_EXPRESSION_to_Expression(serverAlert.getSearchWordsType()));

		setFirstExcludeWord(serverAlert.getExcludeWord(0));
		setSecondExcludeWord(serverAlert.getExcludeWord(1));
		setExcludeWordExpression(convert_NEWS_WORDS_EXPRESSION_to_Expression(serverAlert.getExcludeWordsExpression()));

		setEverywhereFilterType(serverAlert.get_SEARCH_NEWS_TYPE());
	}

	public NewsAlert convertToServerAlert(boolean isUpdate) {
		NewsAlert serverAlert = new NewsAlert();

		if (isUpdate)
			serverAlert.setAlertID(id);

		serverAlert.setName(name);

		serverAlert.setKeepHistory(keepHistory);
		serverAlert.setRemoveAfterFireUp(afterTriggerRemove);
		serverAlert.setCloseAlertAfterFireups(lifetime);
		serverAlert.disableAlert(!statusOn);

		serverAlert.setEmail((emailOn == false) ? NONE : email);
		serverAlert.setPhone((phoneSmsOn == false) ? NONE : phoneSms);
		serverAlert.setSoundSetup((melodyOn == false) ? StringHelper.EMPTY : melody);
		serverAlert.setPopupWindow(popupWindowOn);

		//
		serverAlert.setPTNewsBases(newsLine);
		serverAlert.setUseImportantOnly(onlyRedNewsOn);
		serverAlert.setColor(newsColor);

		serverAlert.setSearchWord(0, firstKeyWord);
		serverAlert.setSearchWord(1, secondKeyWord);
		serverAlert.setSearchWordsExpression(convert_Expression_to_NEWS_WORDS_EXPRESSION(keyWordExpression));

		serverAlert.setExcludeWord(0, firstExcludeWord);
		serverAlert.setExcludeWord(1, secondExcludeWord);
		serverAlert.setExcludeWordsExpression(convert_Expression_to_NEWS_WORDS_EXPRESSION(excludeWordExpression));

		serverAlert.set_SEARCH_NEWS_TYPE(everywhereFilterType);

		return serverAlert;
	}

	private NEWS_WORDS_EXPRESSION convert_Expression_to_NEWS_WORDS_EXPRESSION(Expression expression) {
		switch (expression) {
		case NOT:
			return NEWS_WORDS_EXPRESSION.NOT;
		case OR:
			return NEWS_WORDS_EXPRESSION.OR;
		case AND:
			return NEWS_WORDS_EXPRESSION.AND;
		default:
			return NEWS_WORDS_EXPRESSION.NOT;
		}
	}

	private static Expression convert_NEWS_WORDS_EXPRESSION_to_Expression(NEWS_WORDS_EXPRESSION expressionType) {
		if (expressionType == null)
			return Expression.NOT;

		switch (expressionType) {
		case NOT:
			return Expression.NOT;
		case OR:
			return Expression.OR;
		case AND:
			return Expression.AND;
		default:
			return Expression.NOT;
		}
	}

	public int getLifetime() {
		return lifetime;
	}

	public String getLifetimeString() {
		return String.valueOf(lifetime);
	}

	public void setLifetime(String lifetime) {
		try {
			int temp = Integer.valueOf(lifetime);
			this.lifetime = temp;
		} catch (NumberFormatException e) {
			this.lifetime = ETERNITY_LIFETIME;
		}
	}

	public void setLifetime(int lifetime) {
		this.lifetime = lifetime;
	}

	// Not null!
	public String getNewsLine() {
		return newsLine;
	}

	// Not null!
	public void setNewsLine(String newsLine) {
		this.newsLine = (newsLine == null) ? StringHelper.EMPTY : newsLine;
	}

	public boolean isOnlyRedNewsOn() {
		return onlyRedNewsOn;
	}

	public void setOnlyRedNewsOn(boolean onlyRedNewsOn) {
		this.onlyRedNewsOn = onlyRedNewsOn;
	}

	public Color getNewsColor() {
		return newsColor;
	}

	public void setNewsColor(Color newsColor) {
		this.newsColor = newsColor;
	}

	// Not null!
	public String getFirstKeyWord() {
		return firstKeyWord;
	}

	// Not null!
	public void setFirstKeyWord(String firstKeyWord) {
		this.firstKeyWord = (firstKeyWord == null) ? StringHelper.EMPTY : firstKeyWord;
	}

	// Not null!
	public String getSecondKeyWord() {
		return secondKeyWord;
	}

	// Not null!
	public void setSecondKeyWord(String secondKeyWord) {
		this.secondKeyWord = (secondKeyWord == null) ? StringHelper.EMPTY : secondKeyWord;
	}

	// Not null!
	public Expression getKeyWordExpression() {
		return keyWordExpression;
	}

	// Not null!
	public void setKeyWordExpression(Expression keyWordExpression) {
		this.keyWordExpression = (keyWordExpression == null) ? Expression.NOT : keyWordExpression;
	}

	// Not null!
	public String getFirstExcludeWord() {
		return firstExcludeWord;
	}

	// Not null!
	public void setFirstExcludeWord(String firstExcludeWord) {
		this.firstExcludeWord = (firstExcludeWord == null) ? StringHelper.EMPTY : firstExcludeWord;
	}

	// Not null!
	public String getSecondExcludeWord() {
		return secondExcludeWord;
	}

	// Not null!
	public void setSecondExcludeWord(String secondExcludeWord) {
		this.secondExcludeWord = (secondExcludeWord == null) ? StringHelper.EMPTY : secondExcludeWord;
	}

	// Not null!
	public Expression getExcludeWordExpression() {
		return excludeWordExpression;
	}

	// Not null!
	public void setExcludeWordExpression(Expression excludeWordExpression) {
		this.excludeWordExpression = (excludeWordExpression == null) ? Expression.NOT : excludeWordExpression;
	}

	// Not null!
	public SEARCH_NEWS_TYPE getEverywhereFilterType() {
		return everywhereFilterType;
	}

	// Not null!
	public void setEverywhereFilterType(SEARCH_NEWS_TYPE everywhereFilterType) {
		this.everywhereFilterType = (everywhereFilterType == null) ? SEARCH_NEWS_TYPE.EVERYWERE : everywhereFilterType;
	}

	// Количество срабатываний алерта
	private int lifetime;

	// Показать только "красные" новости
	private boolean onlyRedNewsOn;
	// Строка выбранных категорий новостей.
	private String newsLine;
	// Цвет строки новости
	private Color newsColor;

	// Ключевая фраза 1
	private String firstKeyWord;
	// Ключевая фраза 2
	private String secondKeyWord;
	// Выражение(нет/или/и)
	private Expression keyWordExpression;

	// Исключить слова 1
	private String firstExcludeWord;
	// Исключить слова 2
	private String secondExcludeWord;
	// Выражение(нет/или/и)
	private Expression excludeWordExpression;

	// Фильтр (Искать везде / Искать только в заголовках / ...)
	private SEARCH_NEWS_TYPE everywhereFilterType;

	public enum Expression {
		NOT, OR, AND
	}

	public static final int ETERNITY_LIFETIME = 100;
}
