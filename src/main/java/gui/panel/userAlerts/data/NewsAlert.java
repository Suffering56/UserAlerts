package gui.panel.userAlerts.data;

import java.awt.Color;

public class NewsAlert extends Alert {

	public NewsAlert() {
		this(null);
	}

	public NewsAlert(String name) {
		this(name, null, false, null, null, null, null, null, null, null, null, false, null, false, null, false, null,
				false, null, true);
	}

	public NewsAlert(String name, String newsLine, boolean onlyRedNewsOn, String firstKeyWord, String secondKeyWord,
			Expression keyWordExpression, FilterKey keyWordFilterType, String firstExcludeWord,
			String secondExcludeWord, Expression excludeWordExpression, FilterExclude excludeWordFilterType,
			boolean emailOn, String email, boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody,
			boolean newsColorOn, Color newsColor, boolean notifyWindowOn) {

		super(name, null, null, emailOn, email, phoneSmsOn, phoneSms, melodyOn, melody, notifyWindowOn);

		this.newsLine = (newsLine == null) ? "" : newsLine;
		this.onlyRedNewsOn = onlyRedNewsOn;

		this.firstKeyWord = (firstKeyWord == null) ? "" : firstKeyWord;
		this.secondKeyWord = (secondKeyWord == null) ? "" : secondKeyWord;
		this.keyWordExpression = (keyWordExpression == null) ? Expression.NOT : keyWordExpression;
		this.keyWordFilterType = (keyWordFilterType == null) ? FilterKey.BY_RELEVANCE : keyWordFilterType;

		this.firstExcludeWord = (firstExcludeWord == null) ? "" : firstExcludeWord;
		this.secondExcludeWord = (secondExcludeWord == null) ? "" : secondExcludeWord;
		this.excludeWordExpression = (excludeWordExpression == null) ? Expression.NOT : excludeWordExpression;
		this.excludeWordFilterType = (excludeWordFilterType == null) ? FilterExclude.EVERYWERE : excludeWordFilterType;

		this.newsColorOn = newsColorOn;
		this.newsColor = (newsColor == null) ? DEFAULT_COLOR : newsColor;
	}

	public boolean isOnlyRedNewsOn() {
		return onlyRedNewsOn;
	}

	public void setOnlyRedNewsOn(boolean onlyRedNews) {
		this.onlyRedNewsOn = onlyRedNews;
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
	// Фильтр (По релевантности / Точное совпадение)
	private FilterKey keyWordFilterType;

	// Исключить слова 1
	private String firstExcludeWord;
	// Исключить слова 2
	private String secondExcludeWord;
	// Выражение(нет/или/и)
	private Expression excludeWordExpression;
	// Фильтр (Искать везде / Искать только в заголовках / ...)
	private FilterExclude excludeWordFilterType;

	// Цвет строки новости
	private boolean newsColorOn;
	private Color newsColor = DEFAULT_COLOR;

	public static final Color DEFAULT_COLOR = Color.YELLOW;

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
