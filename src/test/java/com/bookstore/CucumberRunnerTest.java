package com.bookstore;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")  // Запускаем Cucumber через JUnit 5
@SelectClasspathResource("features") // Путь к .feature-файлам (src/test/resources/features)

@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.bookstore")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
public class CucumberRunnerTest { }
