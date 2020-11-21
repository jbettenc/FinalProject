package com.ums.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@Controller
public class ScoreboardController {
	// email to score
	private static ConcurrentHashMap<String, Integer> scoreboard = new ConcurrentHashMap<>();
	private static Lock scoreboardLock = new ReentrantLock();

	@GetMapping("/scoreboard/test")
	@ResponseBody
	public void getScoreboardTest() {
		// setup test scoreboard
		scoreboard.put("user1@gmail.com", 1);
		scoreboard.put("user2@gmail.com", 2);
		scoreboard.put("user3@gmail.com", 3);
	}

	@GetMapping("/scoreboard")
	@ResponseBody
	public ResponseEntity<JSONObject> getScoreboard() {
		JSONObject main = new JSONObject();
		JSONArray users = new JSONArray();		
		
		scoreboardLock.lock();
		for (Entry<String, Integer> entry : this.scoreboard.entrySet()) {
			JSONObject curr = new JSONObject();
			curr.put("email",  entry.getKey());
			curr.put("score",  entry.getValue());
			users.add(curr);
		}
		scoreboardLock.unlock();
		main.put("users", users);
		
		return new ResponseEntity<>(main, HttpStatus.OK);
	}

	@GetMapping("/scoreboard/user/{email}")
	public ResponseEntity<Integer> getScoreboardUser(@PathVariable String email) {
		Integer score = scoreboard.get(email);
		if (score == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(score, HttpStatus.OK);
	}
	
	@PostMapping("/scoreboard/user/create/{email}")
	public ResponseEntity<String> postScoreboardUser(@PathVariable String email) {
		scoreboard.put(email, 0);
		return new ResponseEntity<>("", HttpStatus.CREATED);
		
	}

	@DeleteMapping("/scoreboard/user/{email}")
	public ResponseEntity<Integer> deleteScoreboardUser(@PathVariable String email) {
		Integer removed = scoreboard.remove(email);
		if (removed == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(removed, HttpStatus.OK);
	}

	/* ----------- INTERNAL METHODS ----------- */
	public static Map<String, Integer> internalGetScoreboard() {
		return scoreboard;
	}

	public static Integer internalGetUser(String email) {
		return scoreboard.get(email);
	}

	public static void internalPutScoreboard(String email, Integer value) {
		scoreboard.put(email, value);
	}
	
	public static void internalAddScoreboard(String email, Integer value) {
		scoreboard.put(email, scoreboard.get(email) + value);
	}
}