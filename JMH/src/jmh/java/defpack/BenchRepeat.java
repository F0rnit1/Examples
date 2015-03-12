package defpack;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

@SuppressWarnings("unused")
public class BenchRepeat {
    public static final String STR = "XXX";

    @Benchmark
    public void apache(Blackhole bh) {
        String str = StringUtils.repeat(STR, 20);
        bh.consume(str);
    }

    @Benchmark
    public void guava(Blackhole bh) {
        String str = Strings.repeat(STR, 20);
        bh.consume(str);
    }
}

