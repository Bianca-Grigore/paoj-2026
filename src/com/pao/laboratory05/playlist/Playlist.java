package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    private String name;
    private Song[] songs = new Song[0];

    Playlist(String name) {
        this.name = name;
    }

    void addSong(Song song) {
        System.arraycopy(songs, 0, songs = new Song[songs.length + 1], 0, songs.length - 1);
        songs[songs.length - 1] = song;
    }

    void printSortedByTitle() {
        Song[] copy = songs.clone();
        Arrays.sort(copy);
        for (Song s : copy) {
            System.out.println(s);
        }
    }

    void printSortedByDuration() {
        Song[] copy = songs.clone();
        Arrays.sort(copy, new SongDurationComparator());
        for (Song s : copy) {
            System.out.println(s);
        }
    }

    int getTotalDuration() {
        int total = 0;
        for (Song s : songs) {
            total += s.durationSeconds();
        }
        return total;
    }

    public String getName(){
        return this.name;
    }
}

