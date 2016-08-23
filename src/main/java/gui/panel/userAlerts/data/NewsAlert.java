package gui.panel.userAlerts.data;

import java.awt.Color;

public class NewsAlert extends Alert {

	public NewsAlert() {
		// empty constructor
	}

	public NewsAlert(int id, String name, boolean onlyRedNews, String firstKeyWord, String secondKeyWord,
			Expression keyWordExpression, FilterKey keyWordFilterType, String firstExcludeWord,
			String secondExcludeWord, Expression excludeWordExpression, FilterExclude excludeWordFilterType,
			boolean emailOn, String email, boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody,
			boolean newsColorOn, Color newsColor, boolean windowPopupOn, String newsLine) {
		this.id = id;
		this.name = name;
		this.onlyRedNews = onlyRedNews;
		this.firstKeyWord = firstKeyWord;
		this.secondKeyWord = secondKeyWord;
		this.keyWordExpression = keyWordExpression;
		this.keyWordFilterType = keyWordFilterType;
		this.firstExcludeWord = firstExcludeWord;
		this.secondExcludeWord = secondExcludeWord;
		this.excludeWordExpression = excludeWordExpression;
		this.excludeWordFilterType = excludeWordFilterType;
		this.emailOn = emailOn;
		this.email = email;
		this.phoneSmsOn = phoneSmsOn;
		this.phoneSms = phoneSms;
		this.melodyOn = melodyOn;
		this.melody = melody;
		this.newsColorOn = newsColorOn;
		this.newsColor = newsColor;
		this.windowPopupOn = windowPopupOn;
		this.newsLine = newsLine;
	}

	public boolean isOnlyRedNews() {
		return onlyRedNews;
	}

	public void setOnlyRedNews(boolean onlyRedNews) {
		this.onlyRedNews = onlyRedNews;
	}

	public String getFirstKeyWord() {
		return firstKeyWord;
	}

	public void setFirstKeyWord(String firstKeyWord) {
		this.firstKeyWord = firstKeyWord;
	}

	public String getSecondKeyWord() {
		return secondKeyWord;
	}

	public void setSecondKeyWord(String secondKeyWord) {
		this.secondKeyWord = secondKeyWord;
	}

	public Expression getKeyWordExpression() {
		return keyWordExpression;
	}

	public void setKeyWordExpression(Expression keyWordExpression) {
		this.keyWordExpression = keyWordExpression;
	}

	public FilterKey getKeyWordFilterType() {
		return keyWordFilterType;
	}

	public void setKeyWordFilterType(FilterKey keyWordFilterType) {
		this.keyWordFilterType = keyWordFilterType;
	}

	public String getFirstExcludeWord() {
		return firstExcludeWord;
	}

	public void setFirstExcludeWord(String firstExcludeWord) {
		this.firstExcludeWord = firstExcludeWord;
	}

	public String getSecondExcludeWord() {
		return secondExcludeWord;
	}

	public void setSecondExcludeWord(String secondExcludeWord) {
		this.secondExcludeWord = secondExcludeWord;
	}

	public Expression getExcludeWordExpression() {
		return excludeWordExpression;
	}

	public void setExcludeWordExpression(Expression excludeWordExpression) {
		this.excludeWordExpression = excludeWordExpression;
	}

	public FilterExclude getExcludeWordFilterType() {
		return excludeWordFilterType;
	}

	public void setExcludeWordFilterType(FilterExclude excludeWordFilterType) {
		this.excludeWordFilterType = excludeWordFilterType;
	}

	public boolean isNewsColorOn() {
		return newsColorOn;
	}

	public void setNewsColorOn(boolean newsColorOn) {
		this.newsColorOn = newsColorOn;
	}

	public Color getNewsColor() {
		return newsColor;
	}

	public void setNewsColor(Color newsColor) {
		this.newsColor = newsColor;
	}

	public String getNewsLine() {
		return newsLine;
	}

	public void setNewsLine(String newsLine) {
		this.newsLine = newsLine;
	}

	@Override
	public String toString() {
		return "NewsAlert [id=" + id + ", name=" + name + ", creationDate=" + creationDate + "]";
	}

	// Показать только "красные" новости
	private boolean onlyRedNews = false;
	// Строка выбранных категорий новостей.
	private String newsLine;

	// Ключевая фраза 1
	private String firstKeyWord = "";
	// Ключевая фраза 2
	private String secondKeyWord = "";
	// Выражение(нет/или/и)
	private Expression keyWordExpression = Expression.NOT;
	// Фильтр (По релевантности / Точное совпадение)
	private FilterKey keyWordFilterType = FilterKey.BY_RELEVANCE;

	// Исключить слова 1
	private String firstExcludeWord = "";
	// Исключить слова 1
	private String secondExcludeWord = "";
	// Выражение(нет/или/и)
	private Expression excludeWordExpression = Expression.NOT;
	// Фильтр (Искать везде / Искать только в заголовках / ...)
	private FilterExclude excludeWordFilterType = FilterExclude.EVERYWERE;

	/**************************************
	 *************** Общие ****************
	 **************************************/

	// Цвет строки новости
	private boolean newsColorOn;
	private Color newsColor;
	

	public enum Expression {
		NOT, OR, AND
	}

	public enum FilterKey {
		BY_RELEVANCE, EXACT_MATCH
	}

	public enum FilterExclude {
		EVERYWERE, TITLES_ONLY, RED_ONLY
	}
}
