package gui.panel.userAlerts.data;

import java.util.Calendar;

@SuppressWarnings("deprecation")
public class NewsAlert {

	public NewsAlert() {
		// empty constructor
	}

	public NewsAlert(int id, String name, boolean onlyRedNews, String firstKeyWord, String secondKeyWord,
			Expression keyWordExpression, FilterKey keyWordFilterType, String firstExcludeWord,
			String secondExcludeWord, Expression excludeWordExpression, FilterExclude excludeWordFilterType,
			boolean emailOn, String email, boolean phoneSmsOn, String phoneSms, boolean melodyOn, String melody,
			boolean newsColorOn, String newsColor, boolean windowPopupOn) {
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
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNewsColor() {
		return newsColor;
	}

	public void setNewsColor(String newsColor) {
		this.newsColor = newsColor;
	}

	public boolean isEmailOn() {
		return emailOn;
	}

	public void setEmailOn(boolean emailOn) {
		this.emailOn = emailOn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPhoneSmsOn() {
		return phoneSmsOn;
	}

	public void setPhoneSmsOn(boolean phoneSmsOn) {
		this.phoneSmsOn = phoneSmsOn;
	}

	public String getPhoneSms() {
		return phoneSms;
	}

	public void setPhoneSms(String phoneSms) {
		this.phoneSms = phoneSms;
	}

	public boolean isMelodyOn() {
		return melodyOn;
	}

	public void setMelodyOn(boolean melodyOn) {
		this.melodyOn = melodyOn;
	}

	public String getMelody() {
		return melody;
	}

	public void setMelody(String melody) {
		this.melody = melody;
	}

	public boolean isWindowPopupOn() {
		return windowPopupOn;
	}

	public void setWindowPopupOn(boolean windowPopupOn) {
		this.windowPopupOn = windowPopupOn;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastEventDate() {
		return lastEventDate;
	}

	public void setLastEventDate(String lastEventDate) {
		this.lastEventDate = lastEventDate;
	}

	@Override
	public String toString() {
		return "AlertEntity [id=" + id + ", name=" + name + ", onlyRedNews=" + onlyRedNews + ", firstKeyWord="
				+ firstKeyWord + ", secondKeyWord=" + secondKeyWord + ", keyWordExpression=" + keyWordExpression
				+ ", keyWordFilterType=" + keyWordFilterType + ", firstExcludeWord=" + firstExcludeWord
				+ ", secondExcludeWord=" + secondExcludeWord + ", excludeWordExpression=" + excludeWordExpression
				+ ", excludeWordFilterType=" + excludeWordFilterType + ", newsColorOn=" + newsColorOn + ", newsColor="
				+ newsColor + ", emailOn=" + emailOn + ", email=" + email + ", phoneSmsOn=" + phoneSmsOn + ", phoneSms="
				+ phoneSms + ", melodyOn=" + melodyOn + ", melody=" + melody + ", windowPopupOn=" + windowPopupOn
				+ ", creationDate=" + creationDate + ", lastEventDate=" + lastEventDate + "]";
	}

	/**************************************
	 ************** Основные **************
	 **************************************/
	private int id = -1;
	private String name = "";

	/**************************************
	 ************** Новости ***************
	 **************************************/
	// Показать только "красные" новости
	private boolean onlyRedNews = false;

	// Ключевая фраза 1
	private String firstKeyWord = "";
	// Ключевая фраза 2
	private String secondKeyWord = "";
	// Выражение(нет/или/и)
	private Expression keyWordExpression = Expression.NOT;
	// Фильтр (По релевантности / Точное совпадение)
	private FilterKey keyWordFilterType = FilterKey.BY_RELEVANCE;

	// // По релевантности
	// public static final String KEY_FILTER_BY_RELEVANCE = "BY_RELEVANCE";
	// // Точное совпадение
	// public static final String KEY_FILTER_BY_RELEVANCE = "EXACT_MATCH";

	public enum FilterKey {
		BY_RELEVANCE, EXACT_MATCH
	}

	public enum FilterExclude {
		EVERYWERE, TITLES_ONLY, RED_ONLY
	}

	// Исключить слова 1
	private String firstExcludeWord = "";
	// Исключить слова 1
	private String secondExcludeWord = "";
	// Выражение(нет/или/и)
	private Expression excludeWordExpression = Expression.NOT;
	// Фильтр (Искать везде / Искать только в заголовках / Искать только в
	// "красных" новостях)
	private FilterExclude excludeWordFilterType = FilterExclude.EVERYWERE;
	//
	// // Искать везде
	// public static final String EXCLUDE_FILTER_EVERYWHERE = "EVERYWHERE";
	// // Искать только в заголовках
	// public static final String EXCLUDE_FILTER_TITLES_ONLY = "TITLES_ONLY";
	// // Искать только в "красных" новостях
	// public static final String EXCLUDE_FILTER_RED_NEWS_ONLY =
	// "RED_NEWS_ONLY";

	/**************************************
	 *************** Общие ****************
	 **************************************/
	// Информировать по e-mail
	private boolean emailOn;
	private String email = "";
	// Информировать по SMS
	private boolean phoneSmsOn;
	private String phoneSms = "";
	// Звуковой сигнал
	private boolean melodyOn;
	private String melody = "";
	// Цвет строки новости
	private boolean newsColorOn;
	private String newsColor = "";
	// Всплывающее окно
	private boolean windowPopupOn;

	// Дата создания
	private String creationDate = Calendar.getInstance().getTime().toLocaleString();
	// Дата последнего срабатывания
	private String lastEventDate = "-";

	public enum Expression {
		NOT, OR, AND
	}
	// // Нет
	// public static final String EXPRESSION_NOT = "not";
	// // И
	// public static final String EXPRESSION_OR = "or";
	// // Или
	// public static final String EXPRESSION_AND = "and";
}
