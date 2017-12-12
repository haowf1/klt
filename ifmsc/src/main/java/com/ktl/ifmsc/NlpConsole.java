package com.ktl.ifmsc;

/**
 * Created by hao on 17-9-13.
 */

public interface NlpConsole {
    public void startSemanticParsing();
    public void stopSemanticParsing();

    public void stopSpeak();
    public void cancelSpeak();
    public void resumeSpeaking();
    public void TtsResult(String message);

}
