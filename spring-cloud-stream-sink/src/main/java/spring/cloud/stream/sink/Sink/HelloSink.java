package spring.cloud.stream.sink.Sink;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;

//테스트 소스이고, 시간이 없어서 실제로 동작을 확인하진 않음..
//https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/

@EnableBinding(HelloSink.Sink.class)
public class HelloSink {

    @StreamListener(Sink.SAMPLE)
    public void receive(TestPojo testPojo) {
        System.out.println(testPojo.getValue());
    }

    public interface Sink {
        String SAMPLE = "sample-sink";

        @Input(SAMPLE)
        SubscribableChannel sampleSink();
    }
}
