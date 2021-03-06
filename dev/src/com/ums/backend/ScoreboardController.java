package com.ums.backend;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ScoreboardController {
	// email to score
	private static ConcurrentHashMap<String, Integer> scoreboard = new ConcurrentHashMap<>();
	
	@GetMapping("/scoreboard")
	@ResponseBody
	public ResponseEntity<ConcurrentHashMap<String, Integer>> getScoreboard() {
		scoreboard.put("user1@gmail.com", 1);
		scoreboard.put("user2@gmail.com", 2);
		scoreboard.put("user3@gmail.com", 3);
		return new ResponseEntity<>(scoreboard, HttpStatus.OK);
	}

	@GetMapping("/scoreboard/user/{email}")
	public ResponseEntity<Integer> getScoreboardUser(@PathVariable String email) {
		Integer score = scoreboard.get(email);
		if (score == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(score, HttpStatus.OK);
	}

	@DeleteMapping("/scoreboard/user/{email}")
	public ResponseEntity<Integer> deleteScoreboardUser(@PathVariable String email) {
		Integer removed = scoreboard.remove(email);
		if (removed == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(removed, HttpStatus.OK);
	}
	
	/* ----------- INTERNAL METHODS -----------*/
	public static Map<String, Integer> internalGetScoreboard()
	{
		return scoreboard;
	}
	
	public static Integer internalGetUser(String email)
	{
		return scoreboard.get(email);
	}

	public static void internalPutScoreboard(String email, Integer value)
	{
		scoreboard.put(email, value);
	}
}