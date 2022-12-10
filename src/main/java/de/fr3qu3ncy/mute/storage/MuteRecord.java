package de.fr3qu3ncy.mute.storage;

import de.fr3qu3ncy.easyconfig.core.annotations.ConfigurableField;
import de.fr3qu3ncy.easyconfig.core.serialization.Configurable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ConfigurableField
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MuteRecord implements Configurable<MuteRecord> {

    private String uuid;
    private boolean temporary;
    private long endTime;
    private String reason;

}
