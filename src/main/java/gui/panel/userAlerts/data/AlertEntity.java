package gui.panel.userAlerts.data;

public class AlertEntity {

	public AlertEntity(String name, String type) {
		this.name = name;
		this.type = type;
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

	public String getType() {
		return type;
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

	public String getKeyWordExpression() {
		return keyWordExpression;
	}

	public void setKeyWordExpression(String keyWordExpression) {
		this.keyWordExpression = keyWordExpression;
	}

	public String getKeyWordFilterType() {
		return keyWordFilterType;
	}

	public void setKeyWordFilterType(String keyWordFilterType) {
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

	public String getExcludeWordExpression() {
		return excludeWordExpression;
	}

	public void setExcludeWordExpression(String excludeWordExpression) {
		this.excludeWordExpression = excludeWordExpression;
	}

	public String getExcludeWordFilterType() {
		return excludeWordFilterType;
	}

	public void setExcludeWordFilterType(String excludeWordFilterType) {
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
		return "AlertEntity [id=" + id + ", name=" + name + ", type=" + type + ", onlyRedNews=" + onlyRedNews
				+ ", firstKeyWord=" + firstKeyWord + ", secondKeyWord=" + secondKeyWord + ", keyWordExpression="
				+ keyWordExpression + ", keyWordFilterType=" + keyWordFilterType + ", firstExcludeWord="
				+ firstExcludeWord + ", secondExcludeWord=" + secondExcludeWord + ", excludeWordExpression="
				+ excludeWordExpression + ", excludeWordFilterType=" + excludeWordFilterType + ", newsColorOn="
				+ newsColorOn + ", newsColor=" + newsColor + ", emailOn=" + emailOn + ", email=" + email
				+ ", phoneSmsOn=" + phoneSmsOn + ", phoneSms=" + phoneSms + ", melodyOn=" + melodyOn + ", melody="
				+ melody + ", windowPopupOn=" + windowPopupOn + ", creationDate=" + creationDate + ", lastEventDate="
				+ lastEventDate + "]";
	}

	// main
	private int id = -1;
	private String name;
	private final String type;

	// news
	private boolean onlyRedNews = false;

	private String firstKeyWord;
	private String secondKeyWord;
	private String keyWordExpression = NOT;
	private String keyWordFilterType = BY_RELEVANCE;

	public static final String BY_RELEVANCE = "BY_RELEVANCE";
	public static final String EXACT_MATCH = "EXACT_MATCH";

	private String firstExcludeWord;
	private String secondExcludeWord;
	private String excludeWordExpression;
	private String excludeWordFilterType = EVERYWHERE;

	public static final String EVERYWHERE = "EVERYWHERE";
	public static final String TITLES_ONLY = "TITLES_ONLY";
	public static final String RED_NEWS_ONLY = "RED_NEWS_ONLY";

	private boolean newsColorOn;
	private String newsColor;
	
	public static final String NOT = "not";
	public static final String OR = "or";
	public static final String AND = "and";
	// quotes

	// common
	private boolean emailOn;
	private String email;
	private boolean phoneSmsOn;
	private String phoneSms;
	private boolean melodyOn;
	private String melody;
	private boolean windowPopupOn;

	private String creationDate;
	private String lastEventDate;

	public static final String NEWS = "NEWS";;
	public static final String QUOTES = "QUOTES";

}
