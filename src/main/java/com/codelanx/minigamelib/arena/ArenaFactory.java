/*
 * Copyright (C) 2015 Codelanx, All Rights Reserved
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 *
 * This program is protected software: You are free to distrubute your
 * own use of this software under the terms of the Creative Commons BY-NC-ND
 * license as published by Creative Commons in the year 2015 or as published
 * by a later date. You may not provide the source files or provide a means
 * of running the software outside of those licensed to use it.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * You should have received a copy of the Creative Commons BY-NC-ND license
 * long with this program. If not, see <https://creativecommons.org/licenses/>.
 */
package com.codelanx.minigamelib.arena;

import com.codelanx.codelanxlib.config.Config;
import com.codelanx.codelanxlib.config.lang.Lang;
import com.codelanx.codelanxlib.data.types.Yaml;
import com.codelanx.codelanxlib.serialize.SLocation;
import com.codelanx.codelanxlib.util.Debugger;
import com.codelanx.minigamelib.event.ArenaPreDisposeEvent;
import com.codelanx.minigamelib.lang.MinigameLang;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Generates arena objects
 *
 * @TODO: Synchronize arena saving and loading
 *
 * @since 1.0.0
 * @author 1Rogue
 * @version 1.0.0
 */
public class ArenaFactory {

    /** The main {@link Plugin} instance */
    private final Plugin plugin;
    /** The main {@link File} directory of worlds to use */
    private final File worldFolder;
    /** A {@link File} collection representing the world objects */
    private final List<File> worlds = new ArrayList<>();
    /** A {@link ChunkGenerator} for the void space around arenas */
    private final VoidGenerator gen = new VoidGenerator();
    /** Tracks {@link EditSession} objects */
    private final Map<String, EditSession> editSessions = new HashMap<>();

    /**
     * {@link ArenaFactory} constructor
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param plugin The main {@link Plugin} instance
     */
    public ArenaFactory(Plugin plugin) {
        this.plugin = plugin;
        this.worldFolder = new File(this.plugin.getDataFolder(), "worlds" + File.separatorChar);
        this.worldFolder.mkdirs();
        this.initWorlds();
    }

    /**
     * Initializes the <code>worlds</code> field, and sets it with appropriate
     * world folders.
     *
     * <p>
     * This method disables the plugin if no worlds are found</p>
     *
     * @since 1.0.0
     * @version 1.0.0
     */
    private void initWorlds() {
        File[] f = this.worldFolder.listFiles(File::isDirectory);
        if (f == null) {
            return;
        }
        this.worlds.addAll(Arrays.asList(f));
    }

    /**
     * Returns a new {@link EditSession}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param arena The {@link Arena} to load by name
     *
     * @return A new {@link EditSession} object
     * @throws IOException The world failed to be copied correctly
     */
    public synchronized EditSession getEditSession(String arena) throws IOException {
        EditSession back;
        if ((back = this.editSessions.get(arena)) != null) {
            return back;
        }
        Arena a = this.getArenaExact(arena);
        if (a == null) {
            return null;
        }
        EditSession give = new EditSession(a);
        return give;
    }

    /**
     * Gets the {@link File} folder for an arena by a specific name
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param name The name of the {@link Arena}
     * @return A {@link File} for the arena, or null if not found
     */
    private File getArenaFile(String name) {
        File worl = null;
        for (File f : this.worlds) {
            if (f.getName().equalsIgnoreCase(name)) {
                worl = f;
            }
        }
        return worl;
    }

    /**
     * Gets a specific {@link Arena} by name
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param name The arena to get
     * @return The corresponding arena. If no arena is found, it will return
     * null
     * @throws IOException Error copying world to new file
     */
    public synchronized Arena getArenaExact(String name) throws IOException {
        File worl = this.getArenaFile(name);
        if (worl == null) {
            return null;
        } else {
            return new Arena(this.loadAnonymousWorld(worl), worl);
        }
    }

    /**
     * Gets a specific {@link Arena} by name
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param name The arena to get
     * @return The corresponding arena. If no arena is found, it will attempt to
     * return a random arena.
     * @throws IOException Error copying world to new file
     */
    public synchronized Arena getArena(String name) throws IOException {
        File worl = this.getArenaFile(name);
        if (worl == null) {
            return this.getRandomArena();
        } else {
            return new Arena(this.loadAnonymousWorld(worl), worl);
        }
    }

    /**
     * Attempts to return a random {@link Arena}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return A random {@link Arena}
     * @throws IOException Error copying world to new file
     */
    public synchronized Arena getRandomArena() throws IOException {
        File f = this.randomWorldFile();
        return new Arena(this.loadAnonymousWorld(f), f);
    }

    /**
     * Gets a random {@link File} to load
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link File} object that was selected
     */
    private File randomWorldFile() {
        Random r = new Random();
        return this.worlds.get(r.nextInt(this.worlds.size()));
    }

    /**
     * Returns an unmodifiable list of world folders
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @return The {@link List} of world folders
     */
    public List<File> getWorldFolders() {
        return Collections.unmodifiableList(this.worlds);
    }

    /**
     * Loads a world from a provided {@link File} location
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param baseWorld The {@link File} to load
     * @return A new {@link World} instance
     * @throws IOException Failure to copy world folder
     */
    private World loadAnonymousWorld(File baseWorld) throws IOException {
        if (baseWorld == null || !baseWorld.isDirectory()) {
            throw new IllegalArgumentException();
        }
        String name = "world_" + System.nanoTime();
        File newLocation = new File(plugin.getServer().getWorldContainer(), name);
        while (newLocation.exists()) {
            name = name + "_";
            newLocation = new File(plugin.getServer().getWorldContainer(), name);
        }
        this.copyDirectory(baseWorld, newLocation);
        return WorldCreator.name(name).environment(World.Environment.NORMAL).generator(this.gen).createWorld();
    }

    /**
     * Unloads a {@link World}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param baseWorld The {@link World} to unload
     * @param save Whether or not to save chunks
     * @throws IOException
     */
    private void unloadWorld(World baseWorld, boolean save) {
        if (baseWorld == null) {
            throw new IllegalArgumentException();
        }
        boolean success = this.plugin.getServer().unloadWorld(baseWorld, save);
        if (!success) {
            Debugger.error(new RuntimeException(), "Failed to unload world %s!", baseWorld.getName());
        }
    }

    /**
     * Copies a directory from one file location to another
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param source The source directory to copy
     * @param destination The destination of the directory copy
     * @throws IOException Failure to copy the entire directory
     */
    private void copyDirectory(File source, File destination) throws IOException {
        if (!source.isDirectory()) {
            throw new IllegalArgumentException("Source (" + source.getPath() + ") must be a directory.");
        }

        if (!source.exists()) {
            throw new IllegalArgumentException("Source directory (" + source.getPath() + ") doesn't exist.");
        }

        if (destination.exists()) {
            throw new IllegalArgumentException("Destination (" + destination.getPath() + ") exists.");
        }
        destination.mkdirs();
        for (File file : source.listFiles()) {
            if (file.isDirectory()) {
                this.copyDirectory(file, new File(destination, file.getName()));
            } else {
                this.copyFile(file, new File(destination, file.getName()));
            }
        }
    }

    /**
     * Deletes an entire directory recursively
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param folder The source {@link File} directory to delete
     */
    private void deleteDirectory(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Source (" + folder.getPath() + ") must be a directory");
        }
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                this.deleteDirectory(f);
                f.delete();
            }
            f.delete();
        }
        folder.delete();
    }

    /**
     * Copies a single {@link File} from one location to another
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param source The source {@link File}
     * @param destination The destination of the {@link File} copy
     * @throws FileNotFoundException Source file does not exist
     * @throws IOException Error closing {@link FileChannel} streams
     */
    private void copyFile(File source, File destination) throws FileNotFoundException, IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
                FileChannel targetChannel = new FileOutputStream(destination).getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
        }
    }

    private void teleportPlayerOutOfArena(Player p) {
        //LobbyManager lm = this.plugin.getLobbyManager().backToLobby(p);
    }

    /**
     * Disposes an {@link Arena} and deletes the relevant world. Will save and
     * override previous {@link Arena} objects if the {@link Arena} is an
     * {@link EditSession}
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param arena The {@link Arena} to dispose
     * @param delete Whether or not to delete the temporary {@link World} folder
     */
    public synchronized void disposeArena(Arena arena, boolean delete) {
        if (arena == null) {
            throw new IllegalArgumentException("Arena cannot be null!");
        }
        boolean isEdit = arena instanceof EditSession;
        if (isEdit) {
            EditSession sess = (EditSession) arena;
            this.editSessions.remove(sess.getName());
        }
        ArenaPreDisposeEvent event = new ArenaPreDisposeEvent(arena);
        this.plugin.getServer().getPluginManager().callEvent(event);
        arena.getWorld().getPlayers().stream().forEach((p) -> {
            Lang.sendMessage(p, MinigameLang.ARENA_FACTORY_UNLOAD);
            this.teleportPlayerOutOfArena(p);
        });
        this.unloadWorld(arena.getWorld(), isEdit);
        if (isEdit) {
            ((EditSession) arena).writeConfigValues();
            try {
                arena.getConfig().save();
            } catch (IOException ex) {
                Debugger.error(ex, "Error saving config for arena '%s'!", arena.getName());
            }
            this.deleteDirectory(arena.getOriginalLocation());
            File uid = new File(arena.getWorldFolder(), "uid.dat");
            if (uid.exists()) {
                uid.delete();
            }
            try {
                this.copyDirectory(arena.getWorldFolder(), arena.getOriginalLocation());
            } catch (IOException ex) {
                Debugger.error(ex, "Error saving edited world!");
            }
        }
        File worldDir = arena.getWorld().getWorldFolder();
        if (this.plugin.getServer().getWorld(arena.getWorld().getUID()) != null) {
            this.unloadWorld(arena.getWorld(), false);
        }
        arena.dispose();
        if (delete) {
            this.deleteDirectory(worldDir);
        }
    }

    public List<File> getAvailableArenas() {
        return Collections.unmodifiableList(this.worlds);
    }

    /**
     * Creates a new {@link Arena} from an {@link ArenaBuilder} object
     *
     * @since 1.0.0
     * @version 1.0.0
     *
     * @param build The {2link ArenaBuilder} to parse
     * @throws UnfinishedException If the {@link ArenaBuilder} is not fully
     *                             configured
     * @throws IOException If copying the world files fails
     * @throws NameInUseException If an arena already exists with this name
     */
    public synchronized void handleBuild(ArenaBuilder build) throws UnfinishedException, IOException, NameInUseException {
        if (build == null) {
            throw new IllegalArgumentException("ArenaBuilder cannot be null!");
        }
        build.canBuild();
        build.getWorld().save();
        File f = new File(this.worldFolder, build.getName());
        if (f.exists()) {
            throw new NameInUseException("This arena name (" + build.getName() + ") is already in use!");
        }
        new File(build.getWorld().getWorldFolder(), "uid.dat").delete();
        this.copyDirectory(build.getWorld().getWorldFolder(), f);
        this.worlds.add(f);
        File conf = new File(f, "config.yml");
        conf.createNewFile();
        Config.retrieve(new Yaml(conf), ArenaConfig.EDIT_SPAWN).set(new SLocation(build.getSpawn())).save();
    }
}
