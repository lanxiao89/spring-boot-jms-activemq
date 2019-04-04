package br.com.wos.tecnologia.activemq.listener;

/**
 * Created by wesleysantos in 04/04/19
 */
import br.com.wos.tecnologia.activemq.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageListenerComponent implements ApplicationRunner {

    private final JmsTemplate jmsTemplate;
    private final JmsTemplate jmsTemplateTopic;

    @JmsListener(destination = "queue.person")
    public void onReceiverQueue(Person person) {
        log.info(person.toString());
    }

    @JmsListener(destination = "topic.person", containerFactory = "jmsFactoryTopic")
    public void onReceiverTopic(Person person) {
        log.info(person.toString());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jmsTemplate.convertAndSend("queue.person", new Person("Wesley Oliveira Santos", LocalDate.of(1991, 06,012)));
        jmsTemplateTopic.convertAndSend("topic.person", new Person("Wesley Oliveira Santos", LocalDate.of(1991, 06,012)));
    }
}