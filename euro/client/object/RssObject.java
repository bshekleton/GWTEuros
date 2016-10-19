package com.bryan.euro.client.object;

//import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RssObject
{

	private String title;
	private String url;
	private HTML description;
	private String imageUrl;
	private String category;
	private String publishDate;

	public RssObject(String title, String url, HTML description, String imageUrl, String category, String publishDate)
	{
		this.title = title;
		this.url = url;
		this.description = description;
		this.imageUrl = imageUrl;
		this.category = category;
		stringToDate(publishDate);
	}

	public RssObject()
	{
	}

	private void stringToDate(String publishDate2) {
		this.publishDate = publishDate2;// date;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public HTML getDescription() {
		return description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getCategory() {
		return category;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public Widget toWidgit() {

		Widget hp = new HorizontalPanel();
		VerticalPanel vp = new VerticalPanel();
		hp.setStyleName("newsitem");
		hp.addDomHandler(clickedLink, ClickEvent.getType());

		Label titl = new Label(title);
		titl.setStyleName("rssTitle");
		vp.add(titl);

		HTML des = description;
		des.setStyleName("rssDes");
		vp.add(des);

		Label pubdate = new Label(publishDate);
		pubdate.setStyleName("rssPub");
		vp.add(pubdate);

		Label cat = new Label(category);
		cat.setStyleName("rssCat");
		vp.add(cat);

		((HorizontalPanel) hp).add(vp);
		Image img = new Image(imageUrl);
		img.setStyleName("rssImg");
		((HorizontalPanel) hp).add(new Image(imageUrl));

		return hp;
	}

	private ClickHandler clickedLink = new ClickHandler()
	{
		@Override
		public void onClick(ClickEvent event) {

			Window.open(getUrl(), getTitle(), getTitle());
		}
	};

}
