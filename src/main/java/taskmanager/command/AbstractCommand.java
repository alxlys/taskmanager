package taskmanager.command;

import taskmanager.events.Events;

import java.util.concurrent.Callable;
import java.util.concurrent.SynchronousQueue;

public abstract class AbstractCommand<IN, OUT> implements Callable<Events> {

    String name;
    String[] command;
    SynchronousQueue<IN> in;
    SynchronousQueue<OUT> out;

    AbstractCommand(String[] command) {
        this.name = command[0];
        this.command = command;
    }

    String getArg(int index, String argName) {
        if (index < command.length) {
            return command[index];
        }
        throw new IllegalArgumentException(argName + " not passed.");
    }

    public String getName() {
        return name;
    }

    public void setIn(SynchronousQueue<IN> in) {
        this.in = in;
    }

    public void setOut(SynchronousQueue<OUT> out) {
        this.out = out;
    }

    OUT publish(OUT res) throws InterruptedException {
        if (out != null) {
            out.put(res);
        }
        return res;
    }

    @SafeVarargs
    final IN consume(IN... def) throws InterruptedException {
        if (in != null) {
            return in.take();
        }
        return def.length > 0 ? def[0] : null;
    }
}
