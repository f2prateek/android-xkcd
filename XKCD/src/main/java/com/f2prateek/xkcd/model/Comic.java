/*
 * Copyright 2013 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.xkcd.model;

import android.os.Parcel;
import android.os.Parcelable;

//An object that represents a single Json object from XKCD. http://xkcd.com/info.0.json
public class Comic implements Parcelable {

  private int month;
  private int num;
  private String link;
  private int year;
  private String news;
  private String safe_title;
  private String transcript;
  private String alt;
  private String img;
  private String title;
  private String day;

  public Comic(int month, int num, String link, int year, String news, String safe_title,
      String transcript, String alt, String img, String title, String day) {
    this.month = month;
    this.num = num;
    this.link = link;
    this.year = year;
    this.news = news;
    this.safe_title = safe_title;
    this.transcript = transcript;
    this.alt = alt;
    this.img = img;
    this.title = title;
    this.day = day;
  }

  public int getMonth() {
    return month;
  }

  public int getNum() {
    return num;
  }

  public String getLink() {
    return link;
  }

  public int getYear() {
    return year;
  }

  public String getNews() {
    return news;
  }

  public String getSafe_title() {
    return safe_title;
  }

  public String getTranscript() {
    return transcript;
  }

  public String getAlt() {
    return alt;
  }

  public String getImg() {
    return img;
  }

  public String getTitle() {
    return title;
  }

  public String getDay() {
    return day;
  }

  protected Comic(Parcel in) {
    month = in.readInt();
    num = in.readInt();
    link = in.readString();
    year = in.readInt();
    news = in.readString();
    safe_title = in.readString();
    transcript = in.readString();
    alt = in.readString();
    img = in.readString();
    title = in.readString();
    day = in.readString();
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(month);
    dest.writeInt(num);
    dest.writeString(link);
    dest.writeInt(year);
    dest.writeString(news);
    dest.writeString(safe_title);
    dest.writeString(transcript);
    dest.writeString(alt);
    dest.writeString(img);
    dest.writeString(title);
    dest.writeString(day);
  }

  public static final Creator<Comic> CREATOR = new Creator<Comic>() {
    public Comic createFromParcel(Parcel in) {
      return new Comic(in);
    }

    public Comic[] newArray(int size) {
      return new Comic[size];
    }
  };

  @Override public String toString() {
    return "Comic{" +
        "month=" + month +
        ", num=" + num +
        ", link='" + link + '\'' +
        ", year=" + year +
        ", news='" + news + '\'' +
        ", safe_title='" + safe_title + '\'' +
        ", transcript='" + transcript + '\'' +
        ", alt='" + alt + '\'' +
        ", img='" + img + '\'' +
        ", title='" + title + '\'' +
        ", day='" + day + '\'' +
        '}';
  }
}
