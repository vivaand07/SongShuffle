# SongShuffle

REFLECTION:

All the method signatures stayed the same from the specification for the Playlist and Song classes.

The LinkedChain was kept as it is.

We partially attacked the problem with multiple songs in the Playlist (equals(Object other) method and notifying the user if the song already exists in a playlist). However, we still have time to consider complications between our current methods and the presence of duplicate songs. 
We are yet to tackle randomly traversing to a specific song and actually weaving in audio to play.

Currently, our MVP features, bad-input cases, and tests all work well.

NEXT STEPS:

The MVP is pretty much done.

We want to 100% implement a MusicAnalyzer class that displays listening statistics (top artist, top/bottom genre, top/bottom music decade, favorite song) so that the code has more substance.
We are also trying to figure out if there is a way for us to somehow actually play the music. This falls in our unknowns category, but we all think it’d be a worthwhile stretch goal.
We also are still trying to figure out how to implement a function that can access a random Song object and play it to imitate actual music shuffling.

None - the new tests for MusicAnalyzer were already specified in the spec because we were certain that this was a stretch goal we really wanted to implement.

Catherine will try to research and figure out how to actually be able to play audio files so that the program can actually act like a music app.
Will will write the MusicAnalyzer class.
Vivaan will work on the temporary queue class


Section              Who Did It?        How?
Song class           Catherine        Wrote entire class
SongTest             Catherine        Wrote entire class
Playlist class       Vivaan           Wrote entire class
Playlist test class  Vivaan           Wrote entire class
QueueEntry class     Angelo           Wrote entire class
QueueEntry Test      Angelo           Wrote entire class
LinkedChain class    Angelo           Wrote Entire Class
LinkedChain test     Angelo           Wrote entire class
MusicApp class       Will.            Wrote entire class
MusicApp test        Will.            Wrote entire class

