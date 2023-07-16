package com.benn.dev.kWarmUp

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Component

class KWarmUpBeanRegistrationProcessor : BeanDefinitionRegistryPostProcessor {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {}

    override fun postProcessBeanDefinitionRegistry(registry: BeanDefinitionRegistry) {
        val componentProvider = ClassPathScanningCandidateComponentProvider(false)
        componentProvider.addIncludeFilter(AnnotationTypeFilter(Component::class.java))
        val basePackage = "com.benn.dev.kWarmUp"
        logger.info("$basePackage 로 postProcessBeanDefinitionRegistry")
        val beanDefinitions = componentProvider.findCandidateComponents(basePackage)
        logger.info("$beanDefinitions")
        for (beanDefinition in beanDefinitions) {
            beanDefinition.beanClassName?.let { registry.registerBeanDefinition(it, beanDefinition) }
        }
    }
}