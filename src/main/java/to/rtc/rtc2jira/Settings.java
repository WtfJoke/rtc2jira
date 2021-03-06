/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira;

import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author roman.schaller
 *
 */
public class Settings {

  private static final String PROXY_HOST = "proxy.host";
  private static final String PROXY_PORT = "proxy.port";

  private static final String RTC_URL = "rtc.url";
  private static final String RTC_USER = "rtc.user";
  private static final String RTC_PASSWORD = "rtc.password";
  private static final String RTC_PROJECTAREA = "rtc.projectarea";
  static final String RTC_WORKITEM_ID_RANGE = "rtc.workitemid.range";

  private static final String GITHUB_USER = "github.user";
  private static final String GITHUB_PASSWORD = "github.password";
  private static final String GITHUB_OAUTH_TOKEN = "github.token";
  private static final String GITHUB_REPONAME = "github.reponame";
  private static final String GITHUB_REPOOWNER = "github.repoowner";

  private static final String JIRA_USER = "jira.user";
  private static final String JIRA_PASSWORD = "jira.password";
  private static final String JIRA_URL = "jira.url";
  private static final String JIRA_PROJECTKEY = "jira.projectkey";
  private static final String JIRA_FORCE_CREATE = "jira.forcecreate";

  private static final String SYSOUT_EXPORTER = "sysout.exporter";

  private static final Settings instance = new Settings();

  private Properties props;

  private Settings() {
    props = new Properties();
    try {
      props.load(newBufferedReader(get("settings.properties")));
    } catch (IOException e) {
      System.err
          .println("Please create your settings.properties out of the settings.properties.example");
      throw new RuntimeException(e);
    }
  }

  void setProperties(Properties props) {
    this.props = props;
  }

  public static Settings getInstance() {
    return instance;
  }

  public boolean hasProxySettings() {
    return props.getProperty(PROXY_HOST) != null && props.getProperty(PROXY_PORT) != null;
  }

  public String getProxyHost() {
    return props.getProperty(PROXY_HOST);
  }

  public String getProxyPort() {
    return props.getProperty(PROXY_PORT);
  }

  public boolean hasRtcProperties() {
    return props.containsKey(RTC_USER) && props.containsKey(RTC_PASSWORD)
        && props.containsKey(RTC_URL) && props.containsKey(RTC_WORKITEM_ID_RANGE);
  }

  public String getRtcUrl() {
    return props.getProperty(RTC_URL);
  }

  public String getRtcUser() {
    return props.getProperty(RTC_USER);
  }

  public String getRtcPassword() {
    return props.getProperty(RTC_PASSWORD);
  }

  public String getRtcProjectarea() {
    return props.getProperty(RTC_PROJECTAREA);
  }

  public Iterable<Integer> getRtcWorkItemRange() {
    String rangesString = props.getProperty(RTC_WORKITEM_ID_RANGE);
    String[] ranges = rangesString.split(",");
    IntStream intStream = IntStream.of();
    for (String range : ranges) {
      String[] splitted = range.split("\\.\\.");
      int from = Integer.parseInt(splitted[0].trim());
      if (splitted.length == 1) {
        intStream = IntStream.concat(intStream, IntStream.rangeClosed(from, from));
      } else {
        int to = Integer.parseInt(splitted[1].trim());
        intStream = IntStream.concat(intStream, IntStream.rangeClosed(from, to));
      }
    }
    return intStream.boxed().collect(Collectors.toList());
  }

  public boolean hasGithubProperties() {
    return props.containsKey(GITHUB_USER)//
        && props.containsKey(GITHUB_PASSWORD)//
        && props.containsKey(GITHUB_OAUTH_TOKEN)//
        && props.containsKey(GITHUB_REPONAME)//
        && props.containsKey(GITHUB_REPOOWNER);
  }

  public String getGithubUser() {
    return props.getProperty(GITHUB_USER);
  }

  public String getGithubPassword() {
    return props.getProperty(GITHUB_PASSWORD);
  }

  public String getGithubToken() {
    return props.getProperty(GITHUB_OAUTH_TOKEN);
  }

  public String getGithubRepoName() {
    return props.getProperty(GITHUB_REPONAME);
  }

  public String getGithubRepoOwner() {
    return props.getProperty(GITHUB_REPOOWNER);
  }


  public boolean hasJiraProperties() {
    return props.containsKey(JIRA_USER)//
        && props.containsKey(JIRA_PASSWORD)//
        && props.containsKey(JIRA_URL) //
        && props.containsKey(JIRA_PROJECTKEY) && props.containsKey(JIRA_FORCE_CREATE);
  }

  public String getJiraUser() {
    return props.getProperty(JIRA_USER);
  }

  public String getJiraPassword() {
    return props.getProperty(JIRA_PASSWORD);
  }

  public String getJiraUrl() {
    return props.getProperty(JIRA_URL);
  }

  public String getJiraProjectKey() {
    return props.getProperty(JIRA_PROJECTKEY);
  }

  public boolean isForceCreate() {
    return Boolean.parseBoolean(props.getProperty(JIRA_FORCE_CREATE, Boolean.toString(false)));
  }

  public boolean isSystemOutExporterConfigured() {
    return Boolean.parseBoolean(props.getProperty(SYSOUT_EXPORTER));
  }
}
