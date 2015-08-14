package to.rtc.rtc2jira.exporter;

import java.sql.Timestamp;
import java.util.Date;

import com.orientechnologies.orient.core.record.impl.ODocument;

import to.rtc.rtc2jira.Settings;
import to.rtc.rtc2jira.storage.FieldNames;
import to.rtc.rtc2jira.storage.StorageEngine;
import to.rtc.rtc2jira.storage.StorageQuery;

public interface Exporter {

  void initialize(Settings settings, StorageEngine engine);

  default boolean isConfigured() {
    return false;
  }

  default void export(StorageEngine engine) throws Exception {
    for (ODocument workitem : StorageQuery.getRTCWorkItems(engine)) {
      exportItem(workitem);
      workitem.field(FieldNames.EXPORT_TIMESTAMP, new Timestamp(new Date().getTime()));
    }
  }


  void exportItem(ODocument workItem) throws Exception;

}
