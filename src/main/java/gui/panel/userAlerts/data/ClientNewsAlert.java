package gui.panel.userAlerts.data;

import java.awt.Color;

import gui.panel.userAlerts.util.StringHelper;
import p.alerts.client_api.NewsAlert;
import p.alerts.client_api.NewsAlert.NEWS_WORDS_EXPRESSION;
import p.alerts.client_api.NewsAlert.SEARCH_NEWS_TYPE;

public class ClientNewsAlert extends ClientAlert {

	public ClientNewsAlert() {
		this(StringHelper.EMPTY);
	}

	public ClientNewsAlert(String name) {
		this(name, null, false, null, null, null, null, null, null, null, null, false, null, false, null, false, null, false, null, true);
	}

	public ClientNewsAlert(String name, String newsLine, boolean onlyRedNewsOn, String firstKeyWord, String secondKeyWord,
			Expression keyWordExpression, RelevanceFilterType relevanceFilterType, String firstExcludeWord, String secondExcludeWord,
			Expression excludeWordExpression, SEARCH_NEWS_TYPE everywhereFilterType, boolean emailOn, String email, boolean phoneSmsOn,
			String phoneSms, boolean melodyOn, String melody, boolean newsColorOn, Color newsColor, boolean notifyWindowOn) {

		super(name, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		setNewsLine(newsLine);
		setOnlyRedNewsOn(onlyRedNewsOn);

		setNewsColorOn(newsColorOn);
		setNewsColor(newsColor);

		setFirstKeyWord(firstKeyWord);
		setSecondKeyWord(secondKeyWord);
		setKeyWordExpression(keyWordExpression);

		setFirstExcludeWord(firstExcludeWord);
		setSecondExcludeWord(secondExcludeWord);
		setExcludeWordExpression(excludeWordExpression);

		setRelevanceFilterType(relevanceFilterType);
		setEverywhereFilterType(everywhereFilterType);
	}

	public ClientNewsAlert(NewsAlert serverAlert) {
		setNewsID(serverAlert.getAlertID());
		setName(serverAlert.getName());
		setCreationDate(serverAlert.getCreationDate());
		setLastEventDate(serverAlert.getLastEventDate());

		setNewsLine(serverAlert.getPTNewsBases());
		setOnlyRedNewsOn(serverAlert.getUseImportantOnly());

		setEmail(serverAlert.getEmail());
		setEmailOn(!email.isEmpty());
		setPhoneSms(serverAlert.getPhone());
		setPhoneSmsOn(!phoneSms.isEmpty());
		setMelody(serverAlert.getSoundSetup());
		setMelodyOn(!melody.isEmpty());
		setNewsColor(serverAlert.getColor());
		setNewsColorOn(!newsColor.equals(DEFAULT_COLOR)); //????????????????????????????????????????????????????????????????????????????????????????????????????????
		setPopupWindowOn(serverAlert.getPopupWindow());

		setFirstKeyWord(serverAlert.getSearchWord(0));
		setSecondKeyWord(serverAlert.getSearchWord(1));
		setKeyWordExpression(convert_NEWS_WORDS_EXPRESSION_to_Expression(serverAlert.getSearchWordsType()));

		setFirstExcludeWord(serverAlert.getExcludeWord(0));
		setSecondExcludeWord(serverAlert.getExcludeWord(1));
		setExcludeWordExpression(convert_NEWS_WORDS_EXPRESSION_to_Expression(serverAlert.getExcludeWordsExpression()));

		setEverywhereFilterType(serverAlert.get_SEARCH_NEWS_TYPE());
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

	public boolean isNewsColorOn() {
		return newsColorOn;
	}

	public void setNewsColorOn(boolean newsColorOn) {
		this.newsColorOn = newsColorOn;
	}

	// Not null???????????????????????????????????????????????????????????????????
	public Color getNewsColor() {
		return newsColor;
	}

	// Not null???????????????????????????????????????????????????????????????????
	public void setNewsColor(Color newsColor) {
		this.newsColor = (newsColor == null) ? DEFAULT_COLOR : newsColor;
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
	public RelevanceFilterType getRelevanceFilterType() {
		return relevanceFilterType;
	}

	// Not null!
	public void setRelevanceFilterType(RelevanceFilterType relevanceFilterType) {
		this.relevanceFilterType = (relevanceFilterType == null) ? RelevanceFilterType.BY_RELEVANCE : relevanceFilterType;
	}

	// Not null!
	public SEARCH_NEWS_TYPE getEverywhereFilterType() {
		return everywhereFilterType;
	}

	// Not null!
	public void setEverywhereFilterType(SEARCH_NEWS_TYPE everywhereFilterType) {
		this.everywhereFilterType = (everywhereFilterType == null) ? SEARCH_NEWS_TYPE.EVERYWERE : everywhereFilterType;
	}

	public NewsAlert convertToServerNewsAlert() {
		NewsAlert serverNewsAlert = new NewsAlert();
		serverNewsAlert.setName(name);

		serverNewsAlert.setUseImportantOnly(onlyRedNewsOn);
		serverNewsAlert.set_SEARCH_NEWS_TYPE(everywhereFilterType);

		serverNewsAlert.setEmail(email);
		serverNewsAlert.setPhone(phoneSms);
		serverNewsAlert.setPTNewsBases(newsLine);
		serverNewsAlert.setSoundSetup(melody);
		serverNewsAlert.setColor(newsColor);
		serverNewsAlert.setPopupWindow(popupWindowOn);

		serverNewsAlert.setSearchWord(0, firstKeyWord);
		serverNewsAlert.setSearchWord(1, secondKeyWord);
		serverNewsAlert.setSearchWordsExpression(convert_Expression_to_NEWS_WORDS_EXPRESSION(keyWordExpression));

		serverNewsAlert.setExcludeWord(0, firstExcludeWord);
		serverNewsAlert.setExcludeWord(1, secondExcludeWord);
		serverNewsAlert.setExcludeWordsExpression(convert_Expression_to_NEWS_WORDS_EXPRESSION(excludeWordExpression));

		serverNewsAlert.setCloseAlertAfterFireups(999);
		serverNewsAlert.setKeepHistory(true);

		return serverNewsAlert;
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

	// Показать только "красные" новости
	private boolean onlyRedNewsOn;
	// Строка выбранных категорий новостей.
	private String newsLine;

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

	// Фильтр (По релевантности / Точное совпадение)
	private RelevanceFilterType relevanceFilterType;
	// Фильтр (Искать везде / Искать только в заголовках / ...)
	private SEARCH_NEWS_TYPE everywhereFilterType;

	// Цвет строки новости
	private boolean newsColorOn;
	private Color newsColor = DEFAULT_COLOR;

	public static final Color DEFAULT_COLOR = Color.YELLOW;

	public enum Expression {
		NOT, OR, AND
	}

	public enum RelevanceFilterType {
		BY_RELEVANCE, EXACT_MATCH
	}
}
