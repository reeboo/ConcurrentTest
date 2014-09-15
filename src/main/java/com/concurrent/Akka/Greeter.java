package com.concurrent.Akka;

import akka.actor.UntypedActor;

/**
 * 实现描述: Greeter
 *
 * @version v1.0.0
 * @author: reeboo
 * @since: 2014-09-12 11:09
 */
public class Greeter extends UntypedActor {

    public static enum Msg {
        GREET, DONE;
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == Msg.GREET) {
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
        } else unhandled(msg);
    }

}
