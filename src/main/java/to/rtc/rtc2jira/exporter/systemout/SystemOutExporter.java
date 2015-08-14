/*
 * Copyright (c) 2015 BISON Schweiz AG, All Rights Reserved.
 */
package to.rtc.rtc2jira.exporter.systemout;

import java.util.Map.Entry;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.exporter.Exporter;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;

/**
 * @author roman.schaller
 *
 */
public class SystemOutExporter implements Exporter {

  private Settings settings;

  @Override
  public void initialize(Settings settings, StorageEngine engine) {
    this.settings = settings;
  }

  @Override
  public void exportItem(ODocument workitem) throws Exception {
    System.out.println();
    System.out.println("===== WorkItem: " + workitem.field(FieldNames.ID) + " ======");
    for (Entry<String, Object> entry : workitem) {
      String formattedAttribute = String.format("%-25s: %s", entry.getKey(), entry.getValue());
      System.out.println(formattedAttribute);
    }
  }

  @Override
  public boolean isConfigured() {
    return settings.isSystemOutExporterConfigured();
  }
}
