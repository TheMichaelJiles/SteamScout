package com.steamscout.application.test.api.gamesearchapi;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.steamscout.application.model.api.GameSearchAPI;
import com.steamscout.application.model.game_data.Game;

@Disabled
public class TestTropico {

	@Test
	public void testPullsData() throws IOException {
		GameSearchAPI api = new GameSearchAPI(57690);
		Game tropico = api.makeRequest();
		
		assertAll(() -> assertEquals("Tropico 4", tropico.getTitle()),
				() -> assertEquals(57690, tropico.getAppId()),
				() -> assertEquals("Haemimont Games", tropico.getStudioDescription()));
	}

}
