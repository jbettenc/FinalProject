package com.ums.backend;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import games.Game;
import games.RPS;
import games.TTT;

@CrossOrigin
@Controller
public class GameController {
	//game array
	private static ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();
	private static Queue<Game> rpsGames = new ConcurrentLinkedQueue<>();
	private static Queue<Game> tttGames = new ConcurrentLinkedQueue<>();
	private Gson gson=new GsonBuilder().setPrettyPrinting().create();
	
	@PostMapping("/game/join/{type}/{email}")
	@ResponseBody
	public ResponseEntity<JSONObject> getGameID(@PathVariable String type, @PathVariable String email){
		Game g=null;
		if(type.equals("rps")) {
			if(rpsGames.isEmpty()) {
				g=new RPS(email);
				rpsGames.add(g);
				games.put(g.getGameID().toString(), g);
				JSONObject ret = new JSONObject();
				ret.put("gameId", g.getGameID().toString());
				return new ResponseEntity<>(ret, HttpStatus.CREATED);
			}
			else {
				g=rpsGames.remove();
				g.addUser(email);
				JSONObject ret = new JSONObject();
				ret.put("gameId", g.getGameID().toString());
				return new ResponseEntity<>(ret, HttpStatus.ACCEPTED);
			}
		}
		else if(type.equals("ttt")) {
			if(tttGames.isEmpty()) {
				g=new TTT(email);
				tttGames.add(g);
				games.put(g.getGameID().toString(), g);
				JSONObject ret = new JSONObject();
				ret.put("gameId", g.getGameID().toString());
				return new ResponseEntity<>(ret, HttpStatus.CREATED);
			}
			else {
				g=tttGames.remove();
				g.addUser(email);
				JSONObject ret = new JSONObject();
				ret.put("gameId", g.getGameID().toString());
				return new ResponseEntity<>(ret, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/game/{gameID}")
	@ResponseBody
	public ResponseEntity<String> getGameJSON(@PathVariable String gameID){
		Game g=games.get(gameID);
		Gson gson=new GsonBuilder().setPrettyPrinting().create();
		return new ResponseEntity<>(gson.toJson(g), HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/game/{gameID}/{selection}/{email}")
	@ResponseBody
	public ResponseEntity<Integer> makeMove(@PathVariable String gameID, @PathVariable String selection, @PathVariable String email){
		Game g=games.get(gameID);
		if(g == null)
			return new ResponseEntity<>(404, HttpStatus.NOT_FOUND);
		int err=g.move(email, selection);
		if(err==200) return new ResponseEntity<>(err, HttpStatus.OK);
		else if(err==401) return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
		else if(err==404) return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
		else if(err==406) return new ResponseEntity<>(err, HttpStatus.NOT_ACCEPTABLE);
		else if(err==409) return new ResponseEntity<>(err, HttpStatus.CONFLICT);
		else return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/* ----------- INTERNAL METHODS -----------*/
}