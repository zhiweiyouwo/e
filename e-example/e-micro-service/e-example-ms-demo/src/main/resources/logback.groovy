//
// Built on Fri Aug 05 04:22:00 UTC 2016 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO
import static ch.qos.logback.classic.Level.WARN

scan("10 seconds")
appender("FILE", RollingFileAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "[ %-5level] [%date{yyyy-MM-dd HH:mm:ss}] %logger{96} [%line] - %msg%n"
    charset = Charset.forName("UTF-8")
  }
  rollingPolicy(TimeBasedRollingPolicy) {
    fileNamePattern = "../logs/demo-%d{yyyy-MM-dd}.%i.log"
    timeBasedFileNamingAndTriggeringPolicy(SizeAndTimeBasedFNATP) {
      maxFileSize = "64 MB"
    }
  }
  filter(ThresholdFilter) {
    level = DEBUG
  }
  prudent = true
}
appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "[ %-5level] [%date{yyyy-MM-dd HH:mm:ss}] %logger{96} [%line] - %msg%n"
    charset = Charset.forName("GBK")
  }
  filter(ThresholdFilter) {
    level = INFO
  }
}
root(INFO, ["FILE", "STDOUT"])
logger("com.loy.e.core.web.dispatch", INFO)
logger("com.loy.e", INFO)
logger("org.springframework", WARN)
logger("org.hibernate", WARN)